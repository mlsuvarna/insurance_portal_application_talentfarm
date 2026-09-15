import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Lists.css";
function PolicyTypes() {

  const [policyTypes, setPolicyTypes] =
    useState([]);

  useEffect(() => {
    loadPolicyTypes();
  }, []);

  const loadPolicyTypes = async () => {

    try {

      const response =
        await api.get("/api/policy-types/active");

      setPolicyTypes(response.data);

    } catch (error) {

      console.error(error);
    }
  };

  return (
  <Layout>
    <div className="list-page">
      <div className="list-page-header">
        <h1>Policy Types</h1>

        <p>
          View the active insurance products available
          in the portal.
        </p>
      </div>

      {
        policyTypes.length === 0
          ? (
            <div className="list-empty">
              No Policy Types Found
            </div>
          )
          : (
            <div className="list-grid">
              {policyTypes.map((policyType) => (
                <div
                  key={policyType.policyTypeId}
                  className="list-card"
                >
                  <h3>
                    {policyType.typeName}
                  </h3>

                  <p>
                    <span className="list-card-label">
                      Description:
                    </span>

                    {" "}

                    {policyType.description}
                  </p>
                  <p>
  <span className="list-card-label">
    Category:
  </span>

  {" "}

  {policyType.category}
</p>

                  <p>
                    <span className="list-card-label">
                      Base Premium:
                    </span>

                    {" "}

                    ₹{Number(
  policyType.basePremium
).toLocaleString("en-IN")}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Default Coverage:
                    </span>

                    {" "}

                    ₹{Number(
  policyType.defaultCoverageAmount
).toLocaleString("en-IN")}
                  </p>
                  <p>
  <span className="list-card-label">
    Policy Tenure:
  </span>

  {" "}

  {policyType.tenureMonths} Months
</p>

                  <p>
                    <span className="list-card-label">
                      Status:
                    </span>

                    {" "}

                    <span
                      className={
                        policyType.active
                          ? "status-badge status-active"
                          : "status-badge status-cancelled"
                      }
                    >
                      {policyType.active
                        ? "ACTIVE"
                        : "INACTIVE"}
                    </span>
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

export default PolicyTypes;