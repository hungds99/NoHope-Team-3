// Product Processing
window.onload = showPlan();

let planTable = document.getElementById("plan-table");
let planData = [];

function showPlan() {
  axios
    .get("http://localhost:8080/api/n3/plan")
    .then((res) => {
      if (res.data.length > 0) {
        planData = res.data;
        renderPlanHTML(res.data);
      } else {
        planTable.innerHTML = '<td colspan="9">Không có dự án.</td>';
      }
    })
    .catch((err) => console.log(err));
}

function renderPlanHTML(plans) {
  console.log(plans);
  planTable.innerHTML = "";
  plans.forEach((plan) => planHTML(plan));
}

function planHTML(plan) {

  let content = `<tr>
        <td scope="row" data-id=${plan.id}>${plan.id}</td>
        <td>${plan.planName}</td>
        <td>${plan.createdDate}</td>
        <td>${plan.planAddress}</td>
        <td>${plan.startDate}</td>
        <td>${plan.endDate}</td>
        <td>
          <select onChange=changePlanStatus(this,${plan.id})>
            <option value="CHUA_THUC_HIEN" ${
              plan.planStatus === "CHUA_THUC_HIEN" ? 'selected' : ""
            }>Chưa Thực Hiện</option>
            <option value="DANG_THUC_HIEN" ${
              plan.planStatus === "DANG_THUC_HIEN" ? 'selected' : ""
            }>Đang Thực Hiện</option>
            <option value="DA_HOAN_THANH" ${
              plan.planStatus === "DA_HOAN_THANH" ? 'selected' : ""
            }>Đã Hoàn Thành</option>
            <option value="HUY" ${
              plan.planStatus === "HUY" ? 'selected' : ""
            }>Hủy</option>
          </select>
        </td>
        <td>${plan.planDescription}</td>
        <td>
            <a href="/addproject.html?editPlan=${plan.id}">
              <i class="fas fa-edit"></i>
            </a>
       
            <a href="javascript:void(0)" data-toggle="modal" data-target="#acceptDeleteModalCenter" onClick=showDeleteModal(${
              plan.id
            })>
              <i class="fas fa-trash-alt" ></i>
            </a>
          
            <a href="javascript:void(0)" onClick=showPlanDetail(${
              plan.id
            }) data-toggle="modal" data-target=".plan-modal">
              <i class="fas fa-info-circle"></i>
            </a>
        </td>
     </tr>
     `;
  planTable.innerHTML += content;
}

// Change Status Event
function changePlanStatus(event, planId) {
  const formData = new FormData();
  formData.append("planId", planId);
  formData.append("planStatus", event.value);
  axios
    .post("http://localhost:8080/api/n3/plan/changeStatus", formData)
    .then((res) => console.log(res))
    .catch((err) => console.log(err));
}

// Show Plan Detail
function showPlanDetail(planId) {
  axios
    .get(`http://localhost:8080/api/n3/plan/${planId}`)
    .then((res) => {
      let data = res.data;
      renderPlanDetail(data);
      renderProduct(data.product);
      renderWorker(data.worker);
    })
    .catch((err) => console.log(err));
}

// Accept Delete Modal
function showDeleteModal(planId) {
  let acceptLinkDirect = document.getElementById("accept-link-direct");
  acceptLinkDirect.setAttribute("data-id", planId);

  acceptLinkDirect.addEventListener("click", function () {
    axios
      .get(`http://localhost:8080/api/n3/plan/delete/${planId}`)
      .then(() => location.reload())
      .catch((err) => console.log(err));
  });
}

function renderPlanDetail(plan) {
  let planDetail = document.getElementById("plan-detail");
  planDetail.innerHTML = `
      <div class="col-3">
        <p>Tên Dự Án: <strong> ${plan.planName} </strong></p>
      </div>
      <div class="col-3">
        <p>Ngày Lập: <strong> ${plan.createdDate} </strong></p>
      </div>
      <div class="col-3">
        <p>Ngày Khởi Công: <strong> ${plan.startDate} </strong></p>
      </div>
      <div class="col-3">
        <p>Ngày Hoàn Thành: <strong> ${plan.endDate} </strong></p>
      </div>
      <div class="col-3">
        <p>Địa Điểm: <strong> ${plan.planAddress} </strong></p>
      </div>
      <div class="col-3">
        <p>Khách Hàng: <strong> ${plan.planCustomer} </strong></p>
      </div>
      <div class="col-3">
        <p>Trạng Thái: <strong> ${plan.planStatus} </strong></p>
      </div>
  `;
}

function renderProduct(product) {
  let planProductTable = document.getElementById("plan-product-table");

  planProductTable.innerHTML = "";

  planProductTable.innerHTML = `
  <tr>
    <td scope="row" data-id="${product.id}">${product.id}</td>
    <td>${product.productName}</td>
    <td>${product.productAmount}</td>
    <td>${product.productUnit}</td>
    <td>${product.productDescription}</td>
  </tr>
  `;
}

function renderWorker(workers) {
  let planWorkerTable = document.getElementById("plan-worker-table");

  planWorkerTable.innerHTML = "";

  workers.forEach((worker) => {
    planWorkerTable.innerHTML += `
    <tr>
      <td>${worker.id}</td>
      <td>${worker.workerName}</td>
      <td>${worker.workerBirthday}</td>
      <td>${worker.workerNumber}</td>
      <td>${worker.workerAddress}</td>
    </tr>
    `;
  });
}

// Search
let searchCreatedDate = document.getElementById('search-created-date');
let searchPlanStatus = document.getElementById('search-plan-status');

let searchButton = document.getElementById('search-button');

searchButton.addEventListener('click', searchPlan);

function searchPlan() {
  let planDataSorted = planData.filter(function(plan) {

    if(searchCreatedDate.value === '' && searchPlanStatus.value === 'EMPTY') {
      return plan;
    }
    if(searchCreatedDate.value === '') {
      return plan.planStatus === searchPlanStatus.value;
    }
    
    if(searchPlanStatus.value === 'EMPTY') {
      return searchCreatedDate.value === plan.createdDate;
    }

    return searchCreatedDate.value === plan.createdDate && plan.planStatus === searchPlanStatus.value;
  })

  renderPlanHTML(planDataSorted);
}
