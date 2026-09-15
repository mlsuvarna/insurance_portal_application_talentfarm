import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import Layout from "../components/Layout";
import "../styles/Forms.css";

function CreatePolicyType() {
  const navigate = useNavigate();

  const [typeName, setTypeName] = useState("");
  const [description, setDescription] = useState("");
  const [basePremium, setBasePremium] = useState("");
  const [
    defaultCoverageAmount,
    setDefaultCoverageAmount
  ] = useState("");
  const [active, setActive] = useState(true);
  const [message, setMessage] = useState("");
  const [submitting, setSubmitting] =
    useState(false);
  const [category, setCategory] =
  useState("");

const [tenureMonths, setTenureMonths] =
  useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMessage("");

    if (
      !typeName.trim() ||
      !description.trim() ||
      !category ||
      !basePremium ||
      !defaultCoverageAmount ||
      !tenureMonths
    ) {
      setMessage("Please complete all required fields");
      return;
    }

    try {
      setSubmitting(true);

      const response = await api.post(
        "/api/policy-types",
        {
  typeName: typeName.trim(),
  description: description.trim(),
  category,
  basePremium: Number(basePremium),
  defaultCoverageAmount: Number(
    defaultCoverageAmount
  ),
  tenureMonths: Number(tenureMonths),
  active
}
      );

      alert(
        response.data.message ||
        "Policy type created successfully"
      );

      navigate("/policy-types");
    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
        "Unable to create policy type"
      );
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Layout>
      <div className="form-page">
  <div className="form-card">
      <h1>Create Policy Type</h1>

      {message && (
  <div className="form-message">
    {message}
  </div>
)}

      <form onSubmit={handleSubmit}>
        <div className="form-field">
          <label htmlFor="typeName">
            Policy Type Name
          </label>

          <br />

          <input
            id="typeName"
            type="text"
            value={typeName}
            onChange={(event) =>
              setTypeName(event.target.value)
            }
          />
        </div>

        <br />

        <div className="form-field">
          <label htmlFor="description">
            Description
          </label>

          <br />

          <textarea
            id="description"
            rows="4"
            value={description}
            onChange={(event) =>
              setDescription(event.target.value)
            }
          />
        </div>

        <br />
        <div className="form-field">
  <label htmlFor="category">
    Product Category
  </label>

  <select
    id="category"
    value={category}
    onChange={(event) =>
      setCategory(event.target.value)
    }
  >
    <option value="">
      Select a category
    </option>

    <option value="HEALTH">
      Health Insurance
    </option>

    <option value="MOTOR">
      Motor Insurance
    </option>

    <option value="LIFE">
      Life Insurance
    </option>

    <option value="TRAVEL">
      Travel Insurance
    </option>
  </select>
</div>

        <div className="form-field">
          <label htmlFor="basePremium">
            Base Premium
          </label>

          <br />

          <input
            id="basePremium"
            type="number"
            min="0.01"
            step="0.01"
            value={basePremium}
            onChange={(event) =>
              setBasePremium(event.target.value)
            }
          />
        </div>

        <br />

        <div className="form-field">
          <label htmlFor="coverageAmount">
            Default Coverage Amount
          </label>

          <br />

          <input
            id="coverageAmount"
            type="number"
            min="0.01"
            step="0.01"
            value={defaultCoverageAmount}
            onChange={(event) =>
              setDefaultCoverageAmount(
                event.target.value
              )
            }
          />
        </div>

        <br />
        <div className="form-field">
  <label htmlFor="tenureMonths">
    Policy Tenure
  </label>

  <select
    id="tenureMonths"
    value={tenureMonths}
    onChange={(event) =>
      setTenureMonths(event.target.value)
    }
  >
    <option value="">
      Select policy tenure
    </option>

    <option value="6">
      6 Months
    </option>

    <option value="12">
      12 Months
    </option>

    <option value="24">
      24 Months
    </option>

    <option value="36">
      36 Months
    </option>
  </select>
</div>

        <div className="form-field">
          <label htmlFor="active">
            <input
              id="active"
              type="checkbox"
              checked={active}
              onChange={(event) =>
                setActive(event.target.checked)
              }
            />

            {" "}Active
          </label>
        </div>

        <br />

        <button
          className="form-button"
          type="submit"
          disabled={submitting}
        >
          {submitting
            ? "Creating..."
            : "Create Policy Type"}
        </button>
      </form>
        </div>
</div>
    </Layout>
  );
}

export default CreatePolicyType;