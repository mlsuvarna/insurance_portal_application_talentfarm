import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Lists.css";

function MyPolicies() {

  const [policies, setPolicies] = useState([]);

  const userId =
    localStorage.getItem("userId");

  useEffect(() => {

    loadPolicies();

  }, []);

  const loadPolicies = async () => {

    try {

      const response =
        await api.get(
          `/api/policies/user/${userId}`
        );

      setPolicies(response.data);

    } catch (error) {

      console.error(error);
    }
  };

return (
  <Layout>
    <div className="list-page">

      <div className="list-page-header">
        <h1>My Policies</h1>

        <p>
          View all policies associated with your account.
        </p>
      </div>

      {
        policies.length === 0
          ? (
            <div className="list-empty">
              No Policies Found
            </div>
          )
          : (
            <div className="list-grid">
              {policies.map((policy) => (
                <div
                  key={policy.policyId}
                  className="list-card"
                >
                  <h3>
                    Policy Number: {policy.policyNumber}
                  </h3>

                  <p>
                    <span className="list-card-label">
                      Policy Type:
                    </span>

                    {" "}

                    {policy.policyTypeName}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Premium Amount:
                    </span>

                    {" "}

                    ₹{policy.premiumAmount}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Coverage Amount:
                    </span>

                    {" "}

                    ₹{policy.coverageAmount}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Start Date:
                    </span>

                    {" "}

                    {policy.startDate}
                  </p>

                  <p>
                    <span className="list-card-label">
                      End Date:
                    </span>

                    {" "}

                    {policy.endDate}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Status:
                    </span>

                    {" "}

                    <span
                      className={
                        `status-badge status-${policy.status
                          ?.toLowerCase()
                          .replaceAll("_", "-")}`
                      }
                    >
                      {policy.status}
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

export default MyPolicies;