const BASE_URL = "/api/requests";
const token = localStorage.getItem("token");
const role = localStorage.getItem("role");

// =====================
// CREATE REQUEST (EMPLOYEE)
// =====================
function createRequest() {

  const requestData = {
    title: document.getElementById("title").value,
    description: document.getElementById("description").value,
    amount: document.getElementById("amount").value
  };

  fetch(BASE_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + token
    },
    body: JSON.stringify(requestData)
  })
  .then(res => {
    if (!res.ok) {
      throw new Error("Failed to create request");
    }
    alert("Request submitted successfully");
  })
  .catch(err => alert(err.message));
}

// =====================
// LOAD PENDING REQUESTS (MANAGER)
// =====================
function loadPendingRequests() {

  fetch(`${BASE_URL}/pending`, {
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => {
    if (!res.ok) {
      throw new Error("Unauthorized or no data");
    }
    return res.json();
  })
  .then(data => {

    const container = document.getElementById("requests");
    container.innerHTML = "";

    data.forEach(req => {

      const actionButtons =
        role === "MANAGER"
          ? `
              <input id="remarks-${req.id}" placeholder="Remarks">
              <button onclick="approve(${req.id})">Approve</button>
              <button class="reject" onclick="reject(${req.id})">Reject</button>
            `
          : `<span style="color:gray">No permission</span>`;

      container.innerHTML += `
        <div class="card">
          <h4>${req.title}</h4>
          <p>${req.description}</p>
          <p><b>Amount:</b> ${req.amount}</p>
          <p>
            <span style="color:orange;font-weight:bold">
              ${req.status}
            </span>
          </p>
          ${actionButtons}
        </div>
      `;
    });
  })
  .catch(err => alert(err.message));
}

// =====================
// APPROVE REQUEST (MANAGER)
// =====================
function approve(requestId) {

  const remarks =
    document.getElementById(`remarks-${requestId}`).value;

  fetch(`${BASE_URL}/${requestId}/approve?remarks=${remarks}`, {
    method: "PUT",
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => {
    if (!res.ok) {
      throw new Error("Only MANAGER can approve");
    }
    alert("Request approved");
    loadPendingRequests();
  });
}

// =====================
// REJECT REQUEST (MANAGER)
// =====================
function reject(requestId) {

  const remarks =
    document.getElementById(`remarks-${requestId}`).value;

  fetch(`${BASE_URL}/${requestId}/reject?remarks=${remarks}`, {
    method: "PUT",
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => {
    if (!res.ok) {
      throw new Error("Only MANAGER can reject");
    }
    alert("Request rejected");
    loadPendingRequests();
  });
}
