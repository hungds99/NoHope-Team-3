window.onload = showMaterial();

// Show Material
let materialTable = document.getElementById("material-table");

function showMaterial() {
  let currentPage = 1;
  axios
    .get(`http://localhost:8080/api/n3/material?currentPage=${currentPage}`)
    .then((res) => {
      if (res.data.length > 0) {
        materialTable.innerHTML = "";
        res.data.forEach((material) => {
          renderMaterialHTML(material);
        });
        paginationPage(currentPage);
      } else {
        materialTable.innerHTML =
          '<td colspan="6">Không có nguyên liệu trong kho.</td>';
      }
    })
    .catch((err) => console.log(err));
}

function renderMaterialHTML(material) {
  let content = `
        <tr>
            <td>${material.id}</td>
            <td>${material.materialName}</td>
            <td>${material.materialPrice}</td>
            <td>${material.materialUnit}</td>
            <td>${material.materialAmount}</td>
            <td>
              <i class="fas fa-trash-alt" data-toggle="modal" data-target="#acceptDeleteModalCenter" onClick=showDeleteModal(${material.id})></i>
              <i class="fas fa-edit" data-toggle="modal" data-target=".material-modal" onClick=showEditModal(${material.id})></i>
            </td>
        </tr>
    `;

  materialTable.innerHTML += content;
}

// Pagination
let paginationEl = document.getElementById('pagination'); 
function paginationPage(currentPage) {
  paginationEl.innerHTML = `
  <nav aria-label="...">
      <ul class="pagination">
        <li class="page-item ${currentPage === 1 ? 'disabled' : ''}" onClick="paginationHandler(${currentPage - 1})">
          <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
        </li>
        ${currentPage === 1 ? "" : 
        `<li class="page-item" onClick="paginationHandler(${currentPage - 1})">
          <a class="page-link" href="#">${currentPage - 1}</a>
        </li>`
        }
       
        <li class="page-item active" >
          <a class="page-link" href="#">${currentPage}</a>
        </li>
        <li class="page-item" onClick="paginationHandler(${currentPage + 1})">
          <a class="page-link" href="#">${currentPage + 1}</a>
        </li>
        <li class="page-item" onClick="paginationHandler(${currentPage + 1})">
          <a class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>
  `;
}

function paginationHandler(currentPage) {
  axios
    .get(`http://localhost:8080/api/n3/material?currentPage=${currentPage}`)
    .then((res) => {
      if (res.data.length > 0) {
        materialTable.innerHTML = "";
        res.data.forEach((worker) => {
          renderMaterialHTML(worker);
        });
      } else {
        materialTable.innerHTML = '<td colspan="7">Không có nhân công.</td>';
      }
      paginationPage(currentPage);
    })
    .catch((err) => console.log(err));
}

// Add New Product
let materialName = document.getElementById("material-name");
let materialPrice = document.getElementById("material-price");
let materialUnit = document.getElementById("material-unit");
let materialAmount = document.getElementById("material-amount");

let materialSaveBtn = document.getElementById("material-save");

materialSaveBtn.addEventListener("click", addNewMaterial);

function addNewMaterial() {
  let materialData = {
    id: materialSaveBtn.getAttribute("data-id"),
    materialName: materialName.value,
    materialPrice: materialPrice.value,
    materialUnit: materialUnit.value,
    materialAmount: materialAmount.value,
  };

  axios
    .post("http://localhost:8080/api/n3/material", materialData)
    .then(() => location.reload())
    .catch((err) => console.log(err));
}

function showDeleteModal(materialId) {
  console.log(materialId);
  let acceptLinkDirect = document.getElementById("accept-link-direct");
  acceptLinkDirect.setAttribute("data-id", materialId);

  acceptLinkDirect.addEventListener("click", function () {
    axios
      .get(`http://localhost:8080/api/n3/material/delete/${materialId}`)
      .then(() => location.reload())
      .catch((err) => console.log(err));
  });
}

function showEditModal(materialId) {
  materialSaveBtn.setAttribute("data-id", materialId);

  axios
    .get(`http://localhost:8080/api/n3/material/${materialId}`)
    .then((res) => {
      let materialRes = res.data;

      (materialName.value = materialRes.materialName),
        (materialPrice.value = materialRes.materialPrice),
        (materialUnit.value = materialRes.materialUnit),
        (materialAmount.value = materialRes.materialAmount);
    })
    .catch((err) => console.log(err));
}
