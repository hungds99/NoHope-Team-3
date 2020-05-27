window.onload = checkPlanEdit();

let workerDataIds = [];
let productData = {};

function checkPlanEdit() {
  let urlCurrent = window.location.href;
  let urlGetId = urlCurrent.slice(urlCurrent.length - 5 , urlCurrent.length);
  let checkId = urlGetId.match(/\d+/g);
  
  if(checkId) {
    console.log(checkId[0]);
    let planId = checkId[0];

    axios
    .get(`http://localhost:8080/api/n3/plan/${planId}`)
    .then((res) => renderPlanDataEdit(res.data))
    .catch((err) => console.log(err));
  }
}

function renderPlanDataEdit(plan) {
  console.log(plan);
  let productIdInp = document.getElementById("product-id");
  let productInp = document.getElementById("product-name");

  productIdInp.value = plan.product.id
  productIdInp.setAttribute('data-id', plan.product.id)
  productInp.value = plan.product.productName

  productData = {
    productId: plan.product.id,
    productName: plan.product.productName,
  };

  let planName = document.getElementById("plan-name");
  let planDescription = document.getElementById("plan-description");
  let planStartDate = document.getElementById("plan-start-date");
  let planEndDate = document.getElementById("plan-end-date");
  let planAddress = document.getElementById("plan-address");
  let planCustomer = document.getElementById("plan-customer");

  let planSaveBtn = document.getElementById('plan-save');
  planSaveBtn.setAttribute('data-id', plan.id)

  planName.value = plan.planName
  planDescription.value = plan.planDescription
  planStartDate.value = plan.startDate
  planEndDate.value = plan.endDate
  planAddress.value = plan.planAddress
  planCustomer.value = plan.planCustomer

  let workerTable = document.getElementById('worker-table');

  plan.worker.forEach((wk) => {
    renderWorkerHTML(wk, workerTable)
    workerDataIds.push(wk.id);
  });

  nextStepProductAdded();
}

// General
function debounce(callback, wait, immediate = false) {
  let timeout = null;

  return function () {
    const callNow = immediate && !timeout;
    const next = () => callback.apply(this, arguments);

    clearTimeout(timeout);
    timeout = setTimeout(next, wait);

    if (callNow) next();
  };
}

// Product Search
let productIdInp = document.getElementById("product-id");
let productInp = document.getElementById("product-name");
let productSearchResult = document.getElementById("product-search-result");
let productContainer = document.getElementById("product-container");

productInp.addEventListener(
  "input",
  debounce(function () {
    searchProduct();
  }, 1000)
);

function searchProduct() {
  let term = productInp.value;

  productData = {};

  validateProductChecked(productData);

  if (term.trim() === "") return;

  axios
    .get("http://localhost:8080/api/n3/product/search", {
      params: {
        term: term.trim(),
      },
    })
    .then((response) => {
      renderProductSearch(response.data);
      productContainer.style.display = "block";
    })
    .catch((error) => console.log(error));
}

function renderProductSearch(products) {
  productSearchResult.innerHTML = "";

  if (products.length < 1 || products == undefined) {
    productSearchResult.innerHTML =
      '<tr><td colspan="5">Không tìm thấy sản phẩm phù hợp.</td></tr>';
    return;
  }

  products.forEach((product) => {
    productSearchHTML(product);
  });

  // Action In Product Data
  productProcessor();
}

function productSearchHTML(product) {
  let content = `
    <tr>
        <td scope="row" data-id="${product.id}">${product.id}</td>
        <td>${product.productName}</td>
        <td>${product.productAmount}</td>
        <td>${product.productUnit}</td>
        <td>${product.productDescription}</td>
     </tr>
    `;
  productSearchResult.innerHTML += content;
}

function productProcessor() {
  let productRows = productSearchResult.querySelectorAll("tr");
  productRows.forEach((row) => row.addEventListener("click", productSelected));
}

function productSelected() {
  let productTd = this.querySelectorAll("td");
  let productDataId = productTd[0].getAttribute("data-id");
  let productDataName = productTd[1].textContent;

  productData = {
    productId: productDataId,
    productName: productDataName,
  };

  validateProductChecked(productData);
  productContainer.style.display = "none";

  nextStepProductAdded();
}

function validateProductChecked(productData) {
  if (Object.keys(productData).length > 0) {
    productIdInp.setAttribute("data-id", productData.productId);
    productIdInp.value = productData.productId;
    productInp.value = productData.productName;
    return;
  }

  productIdInp.setAttribute("data-id", "");
  productIdInp.value = "";

  productContainer.style.display = "none";
  nextStepProductNone();
}

// Next Step To Add Plan
// let productSave = document.getElementById("product-save");

// productSave.addEventListener("click", nextStepProductAdded);

function nextStepProductAdded() {
  let planNextstep = document.getElementById("plan-nextstep");
  let workerNextstep = document.getElementById("worker-nextstep");

  planNextstep.style.display = "block";
  workerNextstep.style.display = "block";
}

function nextStepProductNone() {
  let planNextstep = document.getElementById("plan-nextstep");
  let workerNextstep = document.getElementById("worker-nextstep");

  planNextstep.style.display = "none";
  workerNextstep.style.display = "none";
}

// Worker
let workerTable = document.getElementById("worker-table");
let workerSearchInp = document.getElementById("worker-search");
let workerSearchResult = document.getElementById("worker-search-result");

workerSearchInp.addEventListener(
  "input",
  debounce(function () {
    workerSearch();
  }, 1000)
);

function workerSearch() {
  let term = workerSearchInp.value;
  if (term.trim() === "") return;

  axios
    .get("http://localhost:8080/api/n3/worker/search", {
      params: {
        term: term.trim(),
      },
    })
    .then((response) => {
      let workers = response.data;
      workerSearchResult.innerHTML = "";

      if (workers.length < 1 || workers == undefined) {
        workerSearchResult.innerHTML =
          '<tr><td colspan="6">Không tìm thấy thông tin phù hợp.</td></tr>';
        return;
      }

      workers.forEach((worker) => renderWorkerHTML(worker, workerSearchResult));
    })
    .catch((error) => console.log(error));
}

function renderWorkerHTML(worker, renderElement) {
  let content = `
    <tr>
      <td data-id=${worker.id}>${worker.id}</td>
      <td data-id=${worker.id}>${worker.workerName}</td>
      <td data-id=${worker.id}>${worker.workerBirthday}</td>
      <td data-id=${worker.id}>${worker.workerNumber}</td>
      <td data-id=${worker.id}>${worker.workerAddress}</td>
      <td data-id=${worker.id}>${worker.workerStatus}</td>  
    </tr>
  `;
  renderElement.innerHTML += content;
}

// Add Worker Detail
workerSearchResult.addEventListener("click", function (event) {
  let workerDataId = event.target.getAttribute("data-id");

  // console.log(typeof workerDataIds);

  // console.log(typeof workerDataId);

  // console.log(workerDataIds.includes(workerDataId));

  // if (workerDataIds.includes(workerDataId)) {
  //   alert("Nhân công đã được thêm !");
  //   return;
  // }

  axios
    .get(`http://localhost:8080/api/n3/worker/${workerDataId}`)
    .then((res) => {
      renderWorkerHTML(res.data, workerTable);
      workerDataIds.push(workerDataId);
    })
    .catch((err) => console.log(err));
});

// Save Plan
let planSaveBtn = document.getElementById("plan-save");

planSaveBtn.addEventListener("click", savePlan);

function savePlan() {
  let planName = document.getElementById("plan-name");
  let planDescription = document.getElementById("plan-description");
  let planStartDate = document.getElementById("plan-start-date");
  let planEndDate = document.getElementById("plan-end-date");
  let planAddress = document.getElementById("plan-address");
  let planCustomer = document.getElementById("plan-customer");

  let workerDataObj = [];
  workerDataIds.forEach((workerId) => workerDataObj.push({ id: workerId }));

  let planData = {
    id: planSaveBtn.getAttribute('data-id'),
    planName: planName.value.trim(),
    planDescription: planDescription.value.trim(),
    startDate: planStartDate.value,
    endDate: planEndDate.value,
    planAddress: planAddress.value,
    planCustomer: planCustomer.value,
    product: {
      id: productData.productId,
    },
    worker: workerDataObj,
  };

  console.log(planData);

  axios
    .post("http://localhost:8080/api/n3/plan", planData)
    .then((res) => location.replace("http://127.0.0.1:5500/project.html"))
    .catch((err) => console.log(err));
}
