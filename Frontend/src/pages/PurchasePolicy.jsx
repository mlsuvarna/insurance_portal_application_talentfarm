import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Forms.css";

function PurchasePolicy() {
  const [policyTypes, setPolicyTypes] = useState([]);
  const [policyTypeId, setPolicyTypeId] = useState("");
  const [coverageAmount, setCoverageAmount] = useState("");
  const [message, setMessage] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const userId = localStorage.getItem("userId");

  useEffect(() => {
    loadPolicyTypes();
  }, []);

  const loadPolicyTypes = async () => {
    try {
      setMessage("");

      const response = await api.get(
        "/api/policy-types/active"
      );

      setPolicyTypes(response.data);
    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
        "Unable to load policy types"
      );
    }
  };

  const handlePurchase = async (event) => {
    event.preventDefault();
    setMessage("");

    if (!policyTypeId || !coverageAmount) {
      setMessage(
        "Please select a policy type and enter a coverage amount"
      );
      return;
    }

    try {
      setSubmitting(true);

      const response = await api.post(
        "/api/policies/purchase",
        {
          userId: Number(userId),
          policyTypeId: Number(policyTypeId),
          coverageAmount: Number(coverageAmount)
        }
      );

      setMessage(
        response.data.message ||
        "Policy purchased successfully"
      );

      setPolicyTypeId("");
      setCoverageAmount("");
    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
        "Unable to purchase policy"
      );
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Layout>
    <div className="form-page">
    <div className="form-card">
      <h1>Purchase Policy</h1>

      {message && <p>{message}</p>}

      <form onSubmit={handlePurchase}>
        <div>
          <label className="form-field" htmlFor="policyTypeId">
            Select Insurance Product
          </label>

          <br />

          <select
            id="policyTypeId"
            value={policyTypeId}
            onChange={(event) =>
              setPolicyTypeId(event.target.value)
            }
          >
            <option value="">
              Select a policy type
            </option>

            {policyTypes.map((policyType) => (
              <option
                key={policyType.policyTypeId}
                value={policyType.policyTypeId}
              >
                {policyType.typeName}
                {" - Premium: "}
                {policyType.basePremium}
              </option>
            ))}
          </select>
        </div>

        <br />

        <div>
          <label className="form-field" htmlFor="coverageAmount">
            Coverage Amount
          </label>

          <br />

          <input
            id="coverageAmount"
            type="number"
            min="0.01"
            step="0.01"
            value={coverageAmount}
            onChange={(event) =>
              setCoverageAmount(event.target.value)
            }
          />
        </div>

        <br />

        <button className="form-button"
          type="submit"
          disabled={
            submitting || policyTypes.length === 0
          }
        >
          {submitting
            ? "Purchasing..."
            : "Purchase Policy"}
        </button>
      </form>

      {policyTypes.length === 0 && !message && (
        <p>No active insurance products are available.</p>
      )}
        </div>
  </div>
</Layout>
  );
}

export default PurchasePolicy;