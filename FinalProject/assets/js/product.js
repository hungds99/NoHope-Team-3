// Product Processing
window.onload = showProduct();

let productTable = document.getElementById("product-table");

function showProduct() {
  axios
    .get("http://localhost:8080/api/n3/product")
    .then((res) => renderProductHTML(res.data))
    .catch((err) => console.log(err));
}

function renderProductHTML(products) {
  products.forEach((product) => productHTML(product));
}

function productHTML(product) {
  let content = `<tr>
        <td scope="row" data-id="${product.id}">${product.id}</td>
        <td>${product.productName}</td>
        <td>${product.productAmount}</td>
        <td>${product.productUnit}</td>
        <td>${product.productDescription}</td>
        <td>
          <i class="fas fa-trash-alt" data-toggle="modal" data-target="#acceptDeleteModalCenter" onClick=showDeleteModal(${product.id})></i>
          <i class="fas fa-edit" data-toggle="modal" data-target=".product-modal" onClick=showEditModal(${product.id})></i>
          <i class="fas fa-info-circle" onClick=showMaterialOfProduct(${product.id})></i>
        </td>
     </tr>
     `;
  productTable.innerHTML += content;
}

// Add Product
let productName = document.getElementById("product-name");
let productAmount = document.getElementById("product-amount");
let productUnit = document.getElementById("product-unit");
let productDescription = document.getElementById("product-description");

let productSaveBtn = document.getElementById("product-save");

productSaveBtn.addEventListener("click", saveProduct);

function saveProduct() {
  let newProduct = {
    id: productSaveBtn.getAttribute("data-id"),
    productName: productName.value,
    productAmount: productAmount.value,
    productUnit: productUnit.value,
    productDescription: productDescription.value,
  };

  axios
    .post("http://localhost:8080/api/n3/product", newProduct)
    .then(() => location.reload())
    .catch((err) => console.log(err));
}

///
function showDeleteModal(productId) {
  let acceptLinkDirect = document.getElementById("accept-link-direct");
  acceptLinkDirect.setAttribute("data-id", productId);

  acceptLinkDirect.addEventListener("click", function () {
    axios
      .get(`http://localhost:8080/api/n3/product/delete/${productId}`)
      .then(() => location.reload())
      .catch((err) => alert("Thành Phẩm Đang Trong Dự Án"));
  });
}

function showEditModal(productId) {
  productSaveBtn.setAttribute("data-id", productId);

  axios
    .get(`http://localhost:8080/api/n3/product/${productId}`)
    .then((res) => {
      let productRes = res.data;

      (productName.value = productRes.productName),
        (productAmount.value = productRes.productAmount),
        (productUnit.value = productRes.productUnit),
        (productDescription.value = productRes.productDescription);
    })
    .catch((err) => console.log(err));
}

// Material Processing
let materialTable = document.getElementById("material-table");
let materialContainer = document.getElementById("material-container");
let materialTitle = document.getElementById("material-title");
let productId;

function showMaterialOfProduct(_productId) {
  productId = _productId;

  axios
    .get("http://localhost:8080/api/n3/product/getMaterial", {
      params: {
        productId: productId,
      },
    })
    .then((res) => renderMaterialHTML(res.data, _productId))
    .catch((err) => console.log(err));
}

function renderMaterialHTML(materials, _productId) {
  materialTable.innerHTML = "";
  materialTitle.innerHTML = `Định mức xuất NVL cho thành phẩm <span><i class="fas fa-plus-circle fa-sm" data-toggle="modal" data-target=".material-modal"></i></span>`;

  console.log(materials);

  materials.forEach(function (material) {
    materialHTML(material, _productId);
  });

  materialContainer.style.display = "block";
  window.scrollTo(
    0,
    document.body.scrollHeight || document.documentElement.scrollHeight
  );
}

function materialHTML(material, _productId) {
  let content = `<tr>
        <td scope="row" data-id="${material.id}">${material.id}</td>
        <td>${material.materialName}</td>
        <td>${material.materialPrice}</td>
        <td>${material.materialProductAmount}</td>
        <td>${material.materialUnit}</td>
        <td>
            <i class="fas fa-trash-alt" data-toggle="modal" data-target="#acceptDeleteModalCenter" onClick=showDeleteMaterialModal(this,${material.id},${_productId})></i>
        </td>
     </tr>
     `;
  materialTable.innerHTML += content;
}

function showDeleteMaterialModal(ev, materialId, _productId) {
  let acceptLinkDirect = document.getElementById("accept-link-direct");
  acceptLinkDirect.setAttribute("data-id", materialId);
  let formData = new FormData();
  formData.append("materialId", materialId);
  formData.append("productId", _productId);

  acceptLinkDirect.addEventListener("click", function () {
    axios
      .post(`http://localhost:8080/api/n3/product/getMaterial`, formData)
      .then(
        () => ev.parentElement.parentElement.remove(),
        $("#acceptDeleteModalCenter").modal("hide")
      )
      .catch((err) => console.log(err));
  });
}

// Add Material Into Product
let materialInp = document.getElementById("material-value");
let materialSave = document.getElementById("material-save");
let materialTableSearch = document.getElementById("material-table-search");

materialInp.addEventListener("input", searchMaterial);

function searchMaterial() {
  let term = materialInp.value;

  if (term.trim() === "") return;

  axios
    .get("http://localhost:8080/api/n3/material/search", {
      params: {
        term: term.trim(),
      },
    })
    .then((response) => {
      console.log(response.data);
      renderMaterialSearch(response.data);
    })
    .catch(function (error) {
      // handle error
      console.log(error);
    });
}

function renderMaterialSearch(materials) {
  materialTableSearch.innerHTML = "";
  materials.forEach((material) => materialSearchHTML(material));
}

function materialSearchHTML(material) {
  let content = `<tr>
        <td scope="row" data-id="${material.id}">${material.id}</td>
        <td>${material.materialName}</td>
        <td>${material.materialPrice}</td>
        <td>${material.materialAmount}</td>
        <td>${material.materialUnit}</td>
     </tr>
     `;
  materialTableSearch.innerHTML += content;

  materialSelected();
}

function materialSelected() {
  let materialRows = materialTableSearch.querySelectorAll("tr");

  materialRows.forEach((row) =>
    row.addEventListener("click", addMaterialValue)
  );
}

function addMaterialValue() {
  let materialDataId = this.querySelectorAll("td")[0].getAttribute("data-id");
  let materialDataName = this.querySelectorAll("td")[1].textContent;

  materialInp.value = materialDataName;
  materialInp.setAttribute("data-id", materialDataId);
}

materialSave.addEventListener("click", saveMaterial);

function saveMaterial() {
  let materialId = materialInp.getAttribute("data-id");
  let materialAmount = document.getElementById("material-amount");

  axios
    .get("http://localhost:8080/api/n3/product/insertMaterial", {
      params: {
        productId: productId,
        materialId: materialId,
        materialAmount: materialAmount.value,
      },
    })
    .then((res) => {
      showMaterialOfProduct(productId);
      $("#material-modal").modal("hide");
    })
    .catch((err) => console.log(err));
}
