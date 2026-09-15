import { useEffect, useState } from "react";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Forms.css";

function MakePayment() {

  const [policies, setPolicies] = useState([]);
  const [policyId, setPolicyId] = useState("");
  const [amount, setAmount] = useState("");
  const [paymentMethod, setPaymentMethod] =
    useState("UPI");

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

  const handlePolicyChange = (event) => {
  const selectedPolicyId = event.target.value;

  setPolicyId(selectedPolicyId);

  const selectedPolicy = policies.find(
    (policy) =>
      policy.policyId === Number(selectedPolicyId)
  );

  if (selectedPolicy) {
    setAmount(selectedPolicy.premiumAmount);
  } else {
    setAmount("");
  }
};

  const handlePayment = async () => {

    try {

      await api.post(
        "/api/payments",
        {
          policyId: Number(policyId),
          amount: Number(amount),
          paymentMethod
        }
      );

      alert("Payment Successful");

    } catch (error) {

      console.error(error);

      alert("Payment Failed");
    }
  };

  return (
    <Layout>
      <div className="form-page">
      <div className="form-card">

      <h1>Make Payment</h1>

      <div className="form-field">
        <label>Policy</label>

        <br />

        <select
          value={policyId}
          onChange={handlePolicyChange}
        >
          <option value="">
            Select Policy
          </option>

          {policies.map((policy) => (
            <option
  key={policy.policyId}
  value={policy.policyId}
>
  {policy.policyNumber}
  {" - "}
  {policy.policyTypeName}
  {" - Premium: ₹"}
  {policy.premiumAmount}
</option>
          ))}
        </select>
      </div>

      <br />

      <div className="form-field">
        <label>Amount</label>

        <br />

        <input
          type="number"
          value={amount}
          readOnly
        />
      </div>

      <br />

      <div className="form-field">
        <label>Payment Method</label>

        <br />

        <select
          value={paymentMethod}
          onChange={(e) =>
            setPaymentMethod(e.target.value)
          }
        >
          <option value="UPI">
            UPI
          </option>

          <option value="CARD">
            CARD
          </option>

          <option value="NET_BANKING">
            NET BANKING
          </option>
        </select>
      </div>

      <br />

      <button className="form-button" type="button" onClick={handlePayment}>
        Pay Now
      </button>
        </div>
      </div>
    </Layout>
  );
}

export default MakePayment;