import { Link } from "react-router-dom";
import "../styles/Home.css";

function Home() {
  return (
    <div className="home-page">
      <div className="home-card">
        <h1>
          Insurance Self-Service Portal
        </h1>

        <p className="home-description">
          Manage insurance policies, submit and track
          claims, make premium payments, and access
          insurance services securely from one portal.
        </p>

        <div className="home-actions">
          <Link
            className="home-button"
            to="/login"
          >
            Login
          </Link>

          <Link
            className={
              "home-button home-button-secondary"
            }
            to="/register"
          >
            Register
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Home;