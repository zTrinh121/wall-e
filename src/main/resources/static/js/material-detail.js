document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const materialId = parseInt(urlParams.get('id'), 10);
    if (materialId) {
        fetchMaterialDetail(materialId);
    } else {
        document.getElementById('material-detail').innerText = 'No material selected';
    }

    async function fetchMaterialDetail(id) {
        try {
            const response = await fetch(`/api/students/allMaterials`);
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

    function displayMaterialDetail(material) {
        const detailContainer = document.getElementById('material-detail');
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
});
