// ========================================= COMMON SETTINGS  ===========================================

// Display notification
let notifi = document.getElementById('notifi');
let notifi_content = document.getElementById('notifi-content');

function notificationAction(content, color) {
    notifi_content.innerHTML = content;
    notifi.style.display = "block";
    notifi.style.width = "270px";
    notifi.style.backgroundColor = color;

    setTimeout(
        function () {
            notifi.style.display = "none";
        }, 3000);
}

// ========================================= PRODUCT MANAGER
// ===========================================

// Declare form input
let materialName = document.getElementById('materialName');
let amount = document.getElementById('amount');
let unit = document.getElementById('unit');
let price = document.getElementById('price');

// Element Define
let tbody = document.getElementById('tbody');

window.onload = loadMaterialManager();

function loadMaterialManager() {
	
	axios.get('/api/n3/material')
	  .then(function (response) {
		  console.log(response);
		  response.data.forEach(material => {
		        renderMaterial(material);
		    });
	  })
	  .catch(function (error) {
	    // handle error
	    console.log(error);
	  });
	
	
}

// Default Page
function renderMaterial(material) {
    let contents = `
        <tr class="text-center">
			<th scope="row">${material.id}</th>
			<td>${material.materialName}</td>
			<td>${material.amount}</td>
			<td>${material.unit}</td>
			<td>${material.price}</td>
			<td>
				
					<i class="fas fa-trash-alt text-danger mr-2" data-id="${material.id}" id="delete" data-toggle="modal" data-target="#deleteAct"></i>
				
				
					<i class="fas fa-edit text-info" data-id="${material.id}" id="edit"></i>
				
			</td>
		</tr>`;

    tbody.innerHTML += contents;
}

// Add New Material
let saveBtn = document.getElementById('save');
saveBtn.addEventListener('click', actAddMaterial);

function actAddMaterial() {

    let material = {
    	materialName: materialName.value,
        amount: amount.value,
        unit: unit.value,
        price: price.value
    }
    	
	axios.post('/api/n3/material/save', material)
  	  .then(function (response) {
  		  console.log(response);
  		  renderMaterial(material);
  		  notificationAction("Thêm Sản Phẩm Thành Công.", "#12e64b");
  	  })
  	  .catch(function (error) {
  	    // handle error
  	    console.log(error);
  	  });

    
}

// Delete And Update Product
tbody.addEventListener('click', actMaterial);

function actMaterial(event) {
    let ev = event.target;
    let data_id = ev.getAttribute('data-id');
    if (!ev) {
        return;
    }

    // Edit
    if (ev.matches('#edit')) {
        
    	axios.get(`/api/n3/material/${data_id}`)
    	  .then(function (response) {
    	    // handle success
    		material = response.data;
    		materialName.value = material.materialName;
	        amount.value = material.amount;
	        unit.value = material.unit;
	        price.value = material.price;
	        
	        document.documentElement.scrollTop = 0;
    	  })
    	  .catch(function (error) {
    	    // handle error
    	    console.log(error);
    	  });

        
    }
    
    // Delete
    if (ev.matches('#delete')) {
        let accept = document.getElementById("accept");
        
        accept.addEventListener("click", function acceptDelete() {
        	
        	axios.get(`/api/n3/material/delete/${data_id}`)
	    	  .then(function (response) {
	    	    // handle success
	    		  ev.closest('tr').remove();
	    		  notificationAction("Xóa Sản Phẩm Thành Công.", "#38e867");
	    	  })
	    	  .catch(function (error) {
	    	    // handle error
	    	    console.log(error);
	    	  });
        	
        })
       
    }
}