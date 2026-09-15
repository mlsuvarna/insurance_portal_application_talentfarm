import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import api from "../services/api";
import "../styles/Dashboard.css";
import "../styles/Lists.css";

function AdminDashboard() {
  const [policyTypeCount, setPolicyTypeCount] =
    useState(0);

  const [policyCount, setPolicyCount] =
    useState(0);

  const [claimCount, setClaimCount] =
    useState(0);

  const [loading, setLoading] =
    useState(true);

  const [message, setMessage] =
    useState("");

  useEffect(() => {
    loadAdminSummary();
  }, []);

const loadAdminSummary = async () => {
  try {
    setLoading(true);
    setMessage("");

    const response = await api.get(
      "/api/admin/dashboard/summary"
    );

    setPolicyTypeCount(
      response.data.policyTypeCount
    );

    setPolicyCount(
      response.data.policyCount
    );

    setClaimCount(
      response.data.claimCount
    );
  } catch (error) {
    console.error(
      "Unable to load admin summary:",
      error
    );

    setMessage(
      error.response?.data?.message ||
      "Unable to load admin summary"
    );

    setPolicyTypeCount(0);
    setPolicyCount(0);
    setClaimCount(0);
  } finally {
    setLoading(false);
  }
};


  return (
    <Layout>
      <div className="list-page-header">
        <h1>Admin Dashboard</h1>

        <p>
          Manage policy products and monitor portal data.
        </p>
      </div>

      {message && (
        <p
          style={{
            color: "red"
          }}
        >
          {message}
        </p>
      )}

      {loading ? (
        <p>Loading admin summary...</p>
      ) : (
        <div className="summary-cards">
          <div className="summary-card">
            <h3>Policy Types</h3>

            <p>{policyTypeCount}</p>
          </div>

          <div className="summary-card">
            <h3>Policies</h3>

            <p>{policyCount}</p>
          </div>

          <div className="summary-card">
            <h3>Claims</h3>

            <p>{claimCount}</p>
          </div>
        </div>
      )}
    </Layout>
  );
}

export default AdminDashboard;