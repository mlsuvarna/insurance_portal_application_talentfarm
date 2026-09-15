import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Lists.css";

function MyPayments() {

  const [payments, setPayments] =
    useState([]);

  const userId =
    localStorage.getItem("userId");

  useEffect(() => {
    loadPayments();
  }, []);

  const loadPayments = async () => {

    try {

      const response =
        await api.get(
          `/api/payments/user/${userId}`
        );

      setPayments(response.data);

    } catch (error) {

      console.error(error);
    }
  };

  return (
  <Layout>
    <div className="list-page">
      <div className="list-page-header">
        <h1>My Payments</h1>

        <p>
          View your policy premium payment history.
        </p>
      </div>

      {
        payments.length === 0
          ? (
            <div className="list-empty">
              No Payments Found
            </div>
          )
          : (
            <div className="list-grid">
              {payments.map((payment) => (
                <div
                  key={payment.paymentId}
                  className="list-card"
                >
                  <h3>
                    Payment Reference:{" "}
                    {payment.paymentReference}
                  </h3>

                  <p>
                    <span className="list-card-label">
                      Policy Number:
                    </span>

                    {" "}

                    {payment.policyNumber}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Policy Type:
                    </span>

                    {" "}

                    {payment.policyTypeName ||
                      "Not Available"}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Amount:
                    </span>

                    {" "}

                    ₹{payment.amount}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Payment Method:
                    </span>

                    {" "}

                    {payment.paymentMethod}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Transaction ID:
                    </span>

                    {" "}

                    {payment.transactionId}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Paid At:
                    </span>

                    {" "}

                    {payment.paidAt
                      ? new Date(
                          payment.paidAt
                        ).toLocaleString()
                      : "Not Available"}
                  </p>

                  <p>
                    <span className="list-card-label">
                      Status:
                    </span>

                    {" "}

                    <span
                      className={
                        `status-badge status-${payment.status
                          ?.toLowerCase()
                          .replaceAll("_", "-")}`
                      }
                    >
                      {payment.status}
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

export default MyPayments;