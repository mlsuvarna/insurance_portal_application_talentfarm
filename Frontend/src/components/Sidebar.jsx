import { Link } from "react-router-dom";
import "../styles/Sidebar.css";

function Sidebar() {
  const role = localStorage.getItem("role");

  return (
    <aside className="sidebar">
      <h3>Menu</h3>

      <Link to="/dashboard">
        Dashboard
      </Link>

      {role === "CUSTOMER" && (
        <>
          <Link to="/purchase-policy">
            Purchase Policy
          </Link>

          <Link to="/my-policies">
            My Policies
          </Link>

          <Link to="/submit-claim">
            Submit Claim
          </Link>

          <Link to="/my-claims">
            My Claims
          </Link>

          <Link to="/make-payment">
            Make Payment
          </Link>

          <Link to="/my-payments">
            My Payments
          </Link>
        </>
      )}

      {role === "CLAIMS_OFFICER" && (
        <>
          <Link to="/claims-dashboard">
            Claims Dashboard
          </Link>

          <Link to="/claims-queue">
            Claims Queue
          </Link>

          <Link to="/claim-history">
            Claim History
          </Link>
        </>
      )}

      {role === "ADMIN" && (
        <>
          <Link to="/admin-dashboard">
            Admin Dashboard
          </Link>

          <Link to="/policy-types">
            View Policy Types
          </Link>

          <Link to="/create-policy-type">
            Create Policy Type
          </Link>
        </>
      )}
    </aside>
  );
}

export default Sidebar;