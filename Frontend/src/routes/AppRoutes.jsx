import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "../pages/Login";
import Register from "../pages/Register";
import Dashboard from "../pages/Dashboard";
import ProtectedRoute from "../components/ProtectedRoute";
import MyPolicies from "../pages/MyPolicies";
import MyClaims from "../pages/MyClaims";
import SubmitClaim from "../pages/SubmitClaim";
import MyPayments from "../pages/MyPayments";
import MakePayment from "../pages/MakePayment";
import AdminDashboard from "../pages/AdminDashboard";
import PolicyTypes from "../pages/PolicyTypes";
import CreatePolicyType from "../pages/CreatePolicyType";
import ClaimsOfficerDashboard from "../pages/ClaimsOfficerDashboard";
import ClaimsQueue from "../pages/ClaimsQueue";
import ClaimHistory from "../pages/ClaimHistory";
import PurchasePolicy from "../pages/PurchasePolicy";
import NotFound from "../pages/NotFound";
import Home from "../pages/Home";
function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>

        <Route
  path="/"
  element={<Home />}
/>

        <Route
          path="/login"
          element={<Login />}
        />

        <Route
          path="/register"
          element={<Register />}
        />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>}
/>
<Route
  path="/my-policies"
  element={
    <ProtectedRoute>
      <MyPolicies />
    </ProtectedRoute>
  }
/>
<Route
  path="/my-claims"
  element={
    <ProtectedRoute>
      <MyClaims />
    </ProtectedRoute>
  }
/>
<Route
  path="/submit-claim"
  element={
    <ProtectedRoute>
      <SubmitClaim />
    </ProtectedRoute>
  }
/>
<Route
  path="/my-payments"
  element={
    <ProtectedRoute>
      <MyPayments />
    </ProtectedRoute>
  }
/>
<Route
  path="/make-payment"
  element={
    <ProtectedRoute>
      <MakePayment />
    </ProtectedRoute>
  }
/>
<Route
  path="/admin-dashboard"
  element={
    <ProtectedRoute
      allowedRoles={["ADMIN"]}
    >
      <AdminDashboard />
    </ProtectedRoute>
  }
/>
<Route
  path="/policy-types"
  element={
    <ProtectedRoute>
      <PolicyTypes />
    </ProtectedRoute>
  }
/>
<Route
  path="/create-policy-type"
  element={
    <ProtectedRoute>
      <CreatePolicyType />
    </ProtectedRoute>
  }
/>
<Route
  path="/claims-dashboard"
  element={
    <ProtectedRoute
      allowedRoles={[
        "CLAIMS_OFFICER"
      ]}
    >
      <ClaimsOfficerDashboard />
    </ProtectedRoute>
  }
/>
<Route
  path="/claims-queue"
  element={
    <ProtectedRoute>
      <ClaimsQueue />
    </ProtectedRoute>
  }
/>
<Route
  path="/claim-history"
  element={
    <ProtectedRoute>
      <ClaimHistory />
    </ProtectedRoute>
  }
/>
<Route
  path="*"
  element={<h1>Page Not Found</h1>}
/>
<Route
  path="/purchase-policy"
  element={
    <ProtectedRoute
      allowedRoles={["CUSTOMER"]}
    >
      <PurchasePolicy />
    </ProtectedRoute>
  }
/>
<Route
  path="*"
  element={<NotFound />}
/>


      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;