import { useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

function Navbar() {
  const navigate = useNavigate();

  const role = localStorage.getItem("role");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("role");

    navigate("/login", {
      replace: true
    });
  };

  return (
    <nav className="navbar">
      <h2 className="navbar-brand">
        Insurance Portal
      </h2>

      <span className="navbar-role">
        Role: {role || "Not Available"}
      </span>

      <button
        className="navbar-logout"
        type="button"
        onClick={handleLogout}
      >
        Logout
      </button>
    </nav>
  );
}

export default Navbar;