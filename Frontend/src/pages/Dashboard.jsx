import { useEffect, useState } from "react";
import api from "../services/api";
import "../styles/Dashboard.css";
import Layout from "../components/Layout";

function Dashboard() {
  const userId = localStorage.getItem("userId");
  const role = localStorage.getItem("role");

  const [policyCount, setPolicyCount] = useState(0);
  const [claimCount, setClaimCount] = useState(0);
  const [paymentCount, setPaymentCount] = useState(0);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (role === "CUSTOMER" && userId) {
      loadCustomerSummary();
    }
  }, [role, userId]);

  const loadCustomerSummary = async () => {
    try {
      setLoading(true);
      setMessage("");

      const [
        policiesResponse,
        claimsResponse,
        paymentsResponse
      ] = await Promise.all([
        api.get(`/api/policies/user/${userId}`),
        api.get(`/api/claims/user/${userId}`),
        api.get(`/api/payments/user/${userId}`)
      ]);

      setPolicyCount(
        Array.isArray(policiesResponse.data)
          ? policiesResponse.data.length
          : 0
      );

      setClaimCount(
        Array.isArray(claimsResponse.data)
          ? claimsResponse.data.length
          : 0
      );

      setPaymentCount(
        Array.isArray(paymentsResponse.data)
          ? paymentsResponse.data.length
          : 0
      );
    } catch (error) {
      console.error(
        "Unable to load dashboard summary:",
        error
      );

      setMessage(
        error.response?.data?.message ||
        "Unable to load dashboard summary"
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <Layout>
      <h1>Insurance Portal Dashboard</h1>

      <p>
        <strong>User ID:</strong> {userId}
      </p>

      <p>
        <strong>Role:</strong> {role}
      </p>

      {message && (
        <p
          style={{
            color: "red"
          }}
        >
          {message}
        </p>
      )}

      {role === "CUSTOMER" && (
        <section>
          <h2>Customer Summary</h2>

          {loading ? (
            <p>Loading dashboard summary...</p>
          ) : (
            <div className="summary-cards">
              <div className="summary-card">
                <h3>Policies</h3>
                <p>{policyCount}</p>
              </div>

              <div className="summary-card">
                <h3>Claims</h3>
                <p>{claimCount}</p>
              </div>

              <div className="summary-card">
                <h3>Payments</h3>
                <p>{paymentCount}</p>
              </div>
            </div>
          )}
        </section>
      )}

      {![
        "CUSTOMER",
        "CLAIMS_OFFICER",
        "ADMIN"
      ].includes(role) && (
        <p>
          No dashboard options are available for this role.
        </p>
      )}
    </Layout>
  );
}

export default Dashboard;