document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const materialId = parseInt(urlParams.get('id'), 10);
    const modify = document.getElementById("modify").innerText;
    const saveButton = document.getElementById('saveButton');
    const deleteButton = document.getElementById('deleteButton');
    if (materialId) {
        fetchMaterialDetail(materialId);
    } else {
        document.getElementById('material-detail').innerText = 'No material selected';
    }

    async function fetchMaterialDetail(id) {
        try {
            const response = await fetch(`/api/student/allMaterials`);
            if(!response.ok){
                throw new Error('Network response was not ok ' + response.statusText);
            }
            const materials = await response.json()
            console.log("Materials list:", materials);
            const materialDetail = materials.find(m => m.id === materialId);
            console.log("Material detial: " + materialDetail)
            displayMaterialDetail(materialDetail);
        } catch (error) {
            console.error('Error fetching material detail:', error);
        }
    }
    if (saveButton) {
        saveButton.addEventListener('click', () => {
            // Implement save functionality
            // Example: saveMaterial(material.id);
        });
    }

    if (deleteButton) {
        deleteButton.addEventListener('click', () => {
            // Implement delete functionality
            // Example: deleteMaterial(material.id);
        });
    }

    function displayMaterialDetail(material) {
        const detailContainer = document.getElementById('material-detail');
        if(modify){
            detailContainer.innerHTML = `
            
            <embed src="${material.pdfPath}" width="800px" height="500px" />
            <div class="detail-material-intro">
                <p> <span style="font-weight: 600">Tên tài liệu: </span> ${material.materialsName}</p>
                <p> <span style="font-weight: 600">Phân loại : </span> ${material.subjectName}</p>
                <div class="button-container">
                    <button id="saveButton">Save</button>
                    <button id="deleteButton">Delete</button>
                </div>
            </div>
            
        `;
        }
        else{
            detailContainer.innerHTML = `
            <!-- <p>${material.materialsName}</p>
            <p>Uploaded by: ${material.teacher.name}</p> -->
            <embed src="${material.pdfPath}" width="800px" height="500px" />
            <div class="detail-material-intro">
                <p> <span style="font-weight: 600">Tên tài liệu: </span> ${material.materialsName}</p>
                <p> <span style="font-weight: 600">Phân loại : </span> ${material.subjectName}</p>
                <p> <span style="font-weight: 600">Uploaded bởi: </span> ${material.teacher.name}</p>
            </div>
        `;
        }
    }

    async function updateMaterialPdf(materialId, file, subjectName, materialsName) {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('subjectName', subjectName);
        formData.append('materialsName', materialsName);

        try {
            const response = await fetch(`/api/teacher/${materialId}/pdf`, {
                method: 'PUT',
                body: formData,
                // You might need headers for authentication or file handling specifics
            });

            if (!response.ok) {
                throw new Error('Failed to update material PDF');
            }

            // Optionally handle success or inform the user
            console.log('Material PDF updated successfully');
        } catch (error) {
            console.error('Error updating material PDF:', error);
            // Optionally handle and display the error to the user
        }
    }

    async function deleteMaterialPdf(materialId) {
        try {
            const response = await fetch(`/api/teacher/${materialId}/pdf/delete`, {
                method: 'DELETE',
                // You might need headers for authentication or specific requirements
            });

            if (!response.ok) {
                throw new Error('Failed to delete material PDF');
            }

            // Optionally handle success or inform the user
            console.log('Material PDF deleted successfully');
        } catch (error) {
            console.error('Error deleting material PDF:', error);
            // Optionally handle and display the error to the user
        }
    }

});
