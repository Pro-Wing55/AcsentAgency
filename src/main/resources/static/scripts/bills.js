let billsData = [];


const ho = window.location.protocol;
  const host = window.location.host;
  const host_Api = ho + "//" + host;

fetch(`${host_Api}/api/getBills`)
    .then(res => res.json())
    .then(data => {
        billsData = Object.values(data);
        updateDashboard(billsData);
    });

function updateDashboard(data) {
    renderTable(data);
    updateSummary(data);
}

/* Render Table */
function renderTable(data) {

    const tbody = document.querySelector("#billTable tbody");
    tbody.innerHTML = "";

    data.forEach(bill => {

        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${bill.bill}</td>
            <td>${bill.date}</td>
            <td>${bill.to.replaceAll("_", " ")}</td>
            <td>₹${bill.tp || 0}</td>
            <td>
                <button class="edit" onclick="editBill('${bill.bill}')">Edit</button>
                <button class="delete" onclick="deleteBill('${bill.bill}')">Delete</button>
				<button class="view" onclick="viewBill('${bill.view}')">view</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

/* Summary Cards */
function updateSummary(data) {

    document.getElementById("totalBills").innerText = data.length;

    let customers = new Set(data.map(b => b.to));
    document.getElementById("totalCustomers").innerText = customers.size;

    let totalRevenue = data.reduce((sum, b) =>
        sum + (parseFloat(b.tp) || 0), 0);

    document.getElementById("totalRevenue").innerText = "₹" + totalRevenue;
}

/* Search */
document.getElementById("searchInput")
    .addEventListener("input", function () {

        let value = this.value.toLowerCase();

        let filtered = billsData.filter(bill =>
            bill.to.toLowerCase().includes(value)
        );

        updateDashboard(filtered);
    });

/* Date Filter */
document.getElementById("dateFilter")
    .addEventListener("change", function () {

        let date = this.value;

        if (!date) {
            updateDashboard(billsData);
            return;
        }

        let filtered = billsData.filter(b => b.date === date);
        updateDashboard(filtered);
    });

/* Sorting */
document.getElementById("sortSelect")
    .addEventListener("change", function () {

        let sorted = [...billsData];

        if (this.value === "asc") {
            sorted.sort((a, b) => a.to.localeCompare(b.to));
        } else {
            sorted.sort((a, b) => b.to.localeCompare(a.to));
        }

        updateDashboard(sorted);
    });

/* Delete */
function deleteBill(billNo) {

    if (!confirm("Are you sure?")) return;

    fetch(`/api/bills/${billNo}`, {
        method: "DELETE"
    })
    .then(() => {
        billsData = billsData.filter(b => b.bill !== billNo);
        updateDashboard(billsData);
    });
}

/* Edit (Simple Prompt Version) */
function editBill(billNo) {

    let newAmount = prompt("Enter new total amount:");

    if (!newAmount) return;

    fetch(`/api/bills/${billNo}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ tp: newAmount })
    })
    .then(() => {
        let bill = billsData.find(b => b.bill === billNo);
        bill.tp = newAmount;
        updateDashboard(billsData);
    });
	
	
}


function viewBill(viewUrl){
		location.href=viewUrl;
}