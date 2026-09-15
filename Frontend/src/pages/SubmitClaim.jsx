import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Forms.css";

function SubmitClaim() {
  const [policies, setPolicies] = useState([]);
  const [policyId, setPolicyId] = useState("");
  const [claimAmount, setClaimAmount] = useState("");
  const [claimReason, setClaimReason] = useState("");
  const [incidentDate, setIncidentDate] = useState("");
  const [message, setMessage] = useState("");
  const [selectedPolicy, setSelectedPolicy] =
  useState(null);

  const userId = localStorage.getItem("userId");

  useEffect(() => {
    loadPolicies();
  }, []);

  const loadPolicies = async () => {
    try {
      const response = await api.get(
        `/api/policies/user/${userId}`
      );

      const activePolicies = response.data.filter(
        (policy) => policy.status === "ACTIVE"
      );

      setPolicies(activePolicies);
    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
        "Unable to load policies"
      );
    }
  };

  const handlePolicyChange = (event) => {
  const selectedPolicyId = event.target.value;

  setPolicyId(selectedPolicyId);

  const policy = policies.find(
    (item) =>
      item.policyId === Number(selectedPolicyId)
  );

  setSelectedPolicy(policy || null);

  setIncidentDate("");
  setMessage("");
};

  const handleSubmitClaim = async () => {
    setMessage("");

    if (
  selectedPolicy &&
  incidentDate < selectedPolicy.startDate
) {
  setMessage(
    `Incident date cannot be before the policy start date: ${selectedPolicy.startDate}`
  );

  return;
}

if (
  selectedPolicy &&
  incidentDate > selectedPolicy.endDate
) {
  setMessage(
    `Incident date cannot be after the policy end date: ${selectedPolicy.endDate}`
  );

  return;
}

    try {
      const response = await api.post(
        "/api/claims",
        {
          policyId: Number(policyId),
          claimAmount: Number(claimAmount),
          claimReason: claimReason.trim(),
          incidentDate
        }
      );

      setMessage(
        response.data.message ||
        "Claim submitted successfully"
      );

      setPolicyId("");
      setClaimAmount("");
      setClaimReason("");
      setIncidentDate("");
    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
        "Claim submission failed"
      );
    }
  };

  return (
    <Layout>
      <div className="form-page">
  <div className="form-card">
      <h1>Submit Claim</h1>

      {message && (
  <div className="form-message">
    {message}
  </div>
)}

      <div className="form-field">
        <label htmlFor="policyId">
          Select Policy
        </label>

        <br />

        <select
          id="policyId"
          value={policyId}
          onChange={handlePolicyChange}
        >
          <option value="">
            Select an active policy
          </option>

          {policies.map((policy) => (
            <option
              key={policy.policyId}
              value={policy.policyId}
            >
              {policy.policyNumber}
              {" - "}
              {policy.policyTypeName}
            </option>
          ))}
        </select>
        {selectedPolicy && (
  <div className="policy-date-info">
    <p>
      <strong>Policy Start Date:</strong>{" "}
      {selectedPolicy.startDate}
    </p>

    <p>
      <strong>Policy End Date:</strong>{" "}
      {selectedPolicy.endDate}
    </p>

    <p>
      Select an incident date between{" "}
      {selectedPolicy.startDate} and{" "}
      {selectedPolicy.endDate}.
    </p>
  </div>
)}
      </div>

      <br />

      <div className="form-field">
        <label htmlFor="claimAmount">
          Claim Amount
        </label>

        <br />

        <input
          id="claimAmount"
          type="number"
          min="0.01"
          step="0.01"
          value={claimAmount}
          onChange={(event) =>
            setClaimAmount(event.target.value)
          }
        />
      </div>

      <br />

      <div className="form-field">
        <label htmlFor="claimReason">
          Claim Reason
        </label>

        <br />

        <textarea
          id="claimReason"
          rows="4"
          value={claimReason}
          onChange={(event) =>
            setClaimReason(event.target.value)
          }
        />
      </div>

      <br />

      <div className="form-field">
        <label htmlFor="incidentDate">
          Incident Date
        </label>

        <br />

        <input
  id="incidentDate"
  type="date"
  value={incidentDate}
  min={selectedPolicy?.startDate || ""}
  max={selectedPolicy?.endDate || ""}
  disabled={!selectedPolicy}
  onChange={(event) =>
    setIncidentDate(event.target.value)
  }
/>
      </div>

      <br />

      <button
        className="form-button"
        type="button"
        onClick={handleSubmitClaim}
        disabled={policies.length === 0}
      >
        Submit Claim
      </button>

      {policies.length === 0 && (
        <p className="form-empty-message">
          No active policies are available. Purchase a
          policy before submitting a claim.
        </p>
      )}
        </div>
    </div>
    </Layout>
  );
}

export default SubmitClaim;