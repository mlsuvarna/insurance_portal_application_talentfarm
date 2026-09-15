import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import api from "../services/api";
function ClaimsOfficerDashboard() {

  const [submittedCount, setSubmittedCount] =
  useState(0);

const [approvedCount, setApprovedCount] =
  useState(0);

const [rejectedCount, setRejectedCount] =
  useState(0);

  const [loading, setLoading] = useState(true);

const [message, setMessage] = useState("");

  useEffect(() => {
  loadClaimsSummary();
}, []);

const loadClaimsSummary = async () => {
  try {
    setLoading(true);
    setMessage("");

    const [
      submittedResponse,
      approvedResponse,
      rejectedResponse
    ] = await Promise.all([
      api.get(
        "/api/claims/status/SUBMITTED"
      ),
      api.get(
        "/api/claims/status/APPROVED"
      ),
      api.get(
        "/api/claims/status/REJECTED"
      )
    ]);

    setSubmittedCount(
      Array.isArray(submittedResponse.data)
        ? submittedResponse.data.length
        : 0
    );

    setApprovedCount(
      Array.isArray(approvedResponse.data)
        ? approvedResponse.data.length
        : 0
    );

    setRejectedCount(
      Array.isArray(rejectedResponse.data)
        ? rejectedResponse.data.length
        : 0
    );
  } catch (error) {
    console.error(
      "Unable to load claims summary:",
      error
    );

    setMessage(
      error.response?.data?.message ||
      "Unable to load claims summary"
    );
  } finally {
    setLoading(false);
  }
};

  return (
    <Layout>

      <h1>Claims Officer Dashboard</h1>

      <h2>Claims Summary</h2>

{message && (
  <div className="form-message">
    {message}
  </div>
)}

{loading ? (
  <p>Loading claims summary...</p>
) : (
  <div className="summary-cards">
    <div className="summary-card">
      <h3>Submitted</h3>
      <p>{submittedCount}</p>
    </div>

    <div className="summary-card">
      <h3>Approved</h3>
      <p>{approvedCount}</p>
    </div>

    <div className="summary-card">
      <h3>Rejected</h3>
      <p>{rejectedCount}</p>
    </div>
  </div>
)}

      <ul>
        <li><Link to="/claims-queue">Claims Queue</Link></li>
        <li>Pending Claims</li>
        <li><Link to="/claim-history">Claim History</Link></li>
      </ul>

    </Layout>
  );
}

export default ClaimsOfficerDashboard;