import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Lists.css";

function ClaimsQueue() {
  const [claims, setClaims] = useState([]);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const [processingClaimId, setProcessingClaimId] =
    useState(null);

  useEffect(() => {
    loadClaims();
  }, []);

  const loadClaims = async () => {
    try {
      setLoading(true);
      setMessage("");

      const response = await api.get(
        "/api/claims/status/SUBMITTED"
      );

      setClaims(response.data);
    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
        "Unable to load claims"
      );
    } finally {
      setLoading(false);
    }
  };

  const updateClaimStatus = async (
    claimId,
    status
  ) => {
    let remarks = "Claim verified";

    if (status === "REJECTED") {
      remarks = window.prompt(
        "Enter the reason for rejecting this claim:"
      );

      if (!remarks || !remarks.trim()) {
        setMessage(
          "Remarks are required when rejecting a claim"
        );
        return;
      }
    }

    try {
      setProcessingClaimId(claimId);
      setMessage("");

      const response = await api.put(
        `/api/claims/${claimId}/status`,
        {
          status,
          remarks: remarks.trim()
        }
      );

      setMessage(
        response.data.message ||
        `Claim ${status.toLowerCase()} successfully`
      );

      setClaims((currentClaims) =>
        currentClaims.filter(
          (claim) => claim.claimId !== claimId
        )
      );
    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
        "Unable to update claim status"
      );
    } finally {
      setProcessingClaimId(null);
    }
  };

  return (
    <Layout>
      <div className="list-page">
      <div className="list-page-header">
  <h1>Claims Queue</h1>

  <p>
    Review and process submitted insurance claims.
  </p>
</div>

      {loading && <p>Loading claims...</p>}

      {message && <p>{message}</p>}

      {!loading &&
        claims.length === 0 && (
          <div className="list-empty">
  No submitted claims found.
</div>
        )}
    <div className="list-grid">
      {claims.map((claim) => (
        <div key={claim.claimId} className="list-card">
          <h3>
            Claim Number: {claim.claimNumber}
          </h3>

          <p>
            Customer: {claim.customerName}
          </p>

          <p>
            Policy Number: {claim.policyNumber}
          </p>

          <p>
            Claim Amount: {claim.claimAmount}
          </p>

          <p>
            Incident Date: {claim.incidentDate}
          </p>

          <p>
            Reason: {claim.claimReason}
          </p>

          <p>
            Status:

<span
  className={
    `status-badge status-${claim.status
      ?.toLowerCase()
      .replaceAll("_", "-")}`
  }
>
  {claim.status}
</span>
          </p>
        <div className="list-actions">
          <button
            type="button"
            disabled={
              processingClaimId === claim.claimId
            }
            onClick={() =>
              updateClaimStatus(
                claim.claimId,
                "APPROVED"
              )
            }
          >
            Approve
          </button>

          {" "}

          <button
            type="button"
            disabled={
              processingClaimId === claim.claimId
            }
            onClick={() =>
              updateClaimStatus(
                claim.claimId,
                "REJECTED"
              )
            }
          >
            Reject
          </button>
          </div>

          <hr />
        </div>
      ))}</div>
    </div></Layout>
  );
}

export default ClaimsQueue;