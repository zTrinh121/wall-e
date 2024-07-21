    document.addEventListener("DOMContentLoaded", function () {
    //Get information from URL
    // Get the current URL
   var urlParams = window.location.href;
   console.log("Current URL:", urlParams);

   // Extract applyId from the URL
   var urlParts = urlParams.split("applyId=");
   var applyId = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
   console.log("Apply ID:", applyId);
//End getting

//Fetch API
    function fetchApplication(applyId) {
          let urlC = `/manager/view-detail-apply-form/${applyId}`;
            console.log(urlC);
          fetch(urlC)
            .then((response) => {
                if(!response.ok){
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Data received from API:", data);
                document.getElementById("applicationTitleHead").innerText = data.title + " (" + data.createdAt + ")";
                document.getElementById("teacherNameApp").innerText ="Tôi là " + data.teacher.name;
                document.getElementById("applyContent").innerText = data.content;
                document.getElementById("toCenterN").innerText = "Gửi đến trung tâm " + data.center.name;
                document.getElementById("teaPhone").innerText = "Số điện thoại liên lạc là: " + data.teacher.phone;
                document.getElementById("teEmail").innerText = "Địa chỉ email liên lạc là:  " + data.teacher.email;
            })
            .catch((erz) => console.error("loi lay ten thong tin: ", erz));
    }
    fetchApplication(applyId);
//End fetching

//Date
//    document.getElementById("currentDate").innerText = getCurrentDate();
//    function getCurrentDate() {
//        var currentDate = new Date();
//        var dayOfWeek = [
//          "Chủ Nhật",
//          "Thứ Hai",
//          "Thứ Ba",
//          "Thứ Tư",
//          "Thứ Năm",
//          "Thứ Sáu",
//          "Thứ Bảy",
//        ];
//        var day = String(currentDate.getDate()).padStart(2, "0");
//        var month = String(currentDate.getMonth() + 1).padStart(2, "0");
//        var year = currentDate.getFullYear();
//        var dayIndex = currentDate.getDay();
//        var dayName = dayOfWeek[dayIndex];
//        return dayName + ", " + day + "-" + month + "-" + year;
//      }
//End date
});

