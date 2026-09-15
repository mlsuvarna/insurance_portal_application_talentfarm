import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Lists.css";

function MyClaims() {

  const [claims, setClaims] = useState([]);

  const userId =
    localStorage.getItem("userId");

  useEffect(() => {
    loadClaims();
  }, []);

  const loadClaims = async () => {

    try {

      const response =
        await api.get(
          `/api/claims/user/${userId}`
        );

      setClaims(response.data);

    } catch (error) {

      console.error(error);
    }
  };

return (
  <Layout>
    <div className="list-page">

      <div className="list-page-header">
        <h1>My Claims</h1>

        <p>
          View and track all claims submitted for your
          policies.
        </p>
      </div>

      {
        claims.length === 0
          ? (
            <div className="list-empty">
              No Claims Found
            </div>
          )
          : (
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

                    ₹{claim.claimAmount}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Claim Reason:
                    </span>

                    {" "}

                    {claim.claimReason}
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
                </div>
              ))}
            </div>
          )
      }

    </div>
  </Layout>
);
}

export default MyClaims;