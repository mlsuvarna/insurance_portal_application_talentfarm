import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Lists.css";
import "../styles/Forms.css";

function ClaimHistory() {
  const [status, setStatus] =
    useState("APPROVED");

  const [claims, setClaims] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  const [message, setMessage] =
    useState("");

  const [processingClaimId, setProcessingClaimId] =
    useState(null);

  useEffect(() => {
    loadClaims();
  }, [status]);

  const loadClaims = async () => {
    try {
      setLoading(true);

      const response = await api.get(
        `/api/claims/status/${status}`
      );

      setClaims(
        Array.isArray(response.data)
          ? response.data
          : []
      );
    } catch (error) {
      console.error(
        "Unable to load claim history:",
        error
      );

      setClaims([]);

      setMessage(
        error.response?.data?.message ||
        "Unable to load claim history"
      );
    } finally {
      setLoading(false);
    }
  };

  const settleClaim = async (claimId) => {
    try {
      setProcessingClaimId(claimId);
      setMessage("");

      const response = await api.put(
        `/api/claims/${claimId}/status`,
        {
          status: "SETTLED",
          remarks:
            "Claim payment completed and claim settled"
        }
      );

      setMessage(
        response.data.message ||
        "Claim settled successfully"
      );

      setClaims((currentClaims) =>
        currentClaims.filter(
          (claim) => claim.claimId !== claimId
        )
      );
    } catch (error) {
      console.error(
        "Unable to settle claim:",
        error
      );

      setMessage(
        error.response?.data?.message ||
        "Unable to settle claim"
      );
    } finally {
      setProcessingClaimId(null);
    }
  };

  return (
    <Layout>
      <div className="list-page">
        <div className="list-page-header">
          <h1>Claim History</h1>

          <p>
            Review claims by their current processing
            status.
          </p>
        </div>

        <div className="form-field">
          <label htmlFor="claimStatus">
            Filter By Status
          </label>

          <select
            id="claimStatus"
            value={status}
            onChange={(event) => {
              setStatus(event.target.value);
              setMessage("");
            }}
          >
            <option value="SUBMITTED">
              Submitted
            </option>

            <option value="IN_REVIEW">
              In Review
            </option>

            <option value="APPROVED">
              Approved
            </option>

            <option value="REJECTED">
              Rejected
            </option>

            <option value="SETTLED">
              Settled
            </option>
          </select>
        </div>

        {message && (
          <div className="form-message">
            {message}
          </div>
        )}

        {loading && (
          <div className="list-loading">
            Loading claims...
          </div>
        )}

        {!loading &&
          claims.length === 0 && (
            <div className="list-empty">
              No{" "}
              {status
                .toLowerCase()
                .replaceAll("_", " ")}{" "}
              claims found.
            </div>
          )}

        {!loading && claims.length > 0 && (
          <div className="list-grid">
            {claims.map((claim) => (
              <div
                key={claim.claimId}
                className="list-card"
              >
                <h3>
                  Claim Number: {claim.claimNumber}
                </h3>

                <p>
                  <span className="list-card-label">
                    Customer:
                  </span>

                  {" "}

                  {claim.customerName}
                </p>

                <p>
                  <span className="list-card-label">
                    Policy Number:
                  </span>

                  {" "}

                  {claim.policyNumber}
                </p>

                <p>
                  <span className="list-card-label">
                    Claim Amount:
                  </span>

                  {" "}

                  ₹{Number(
                    claim.claimAmount
                  ).toLocaleString("en-IN")}
                </p>

                <p>
                  <span className="list-card-label">
                    Incident Date:
                  </span>

                  {" "}

                  {claim.incidentDate}
                </p>

                <p>
                  <span className="list-card-label">
                    Reason:
                  </span>

                  {" "}

                  {claim.claimReason}
                </p>

                <p>
                  <span className="list-card-label">
                    Status:
                  </span>

                  {" "}

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

                <p>
                  <span className="list-card-label">
                    Remarks:
                  </span>

                  {" "}

                  {claim.remarks || "No remarks"}
                </p>

                <p>
                  <span className="list-card-label">
                    Submitted At:
                  </span>

                  {" "}

                  {claim.submittedAt
                    ? new Date(
                        claim.submittedAt
                      ).toLocaleString()
                    : "Not Available"}
                </p>

                {claim.status === "APPROVED" && (
                  <div className="list-actions">
                    <button
                      type="button"
                      disabled={
                        processingClaimId ===
                        claim.claimId
                      }
                      onClick={() =>
                        settleClaim(claim.claimId)
                      }
                    >
                      {processingClaimId ===
                      claim.claimId
                        ? "Settling..."
                        : "Settle Claim"}
                    </button>
                  </div>
                )}
              </div>
            ))}
          </div>
        )}
      </div>
    </Layout>
  );
}

export default ClaimHistory;