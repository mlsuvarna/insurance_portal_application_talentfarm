import Navbar from "./NavBar";
import Sidebar from "./Sidebar";

function Layout({ children }) {

  return (
    <div>

      <Navbar />

      <div className="dashboard-layout">

        <Sidebar />

        <main className="dashboard-container">

          {children}

        </main>

      </div>

    </div>
  );
}

export default Layout;