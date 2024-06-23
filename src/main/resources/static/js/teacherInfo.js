document.addEventListener("DOMContentLoaded", function () {
//Get information from URL
    // Get the current URL
    var urlParams = window.location.href;
    console.log("Current URL:", urlParams);

    // Extract centerId from the URL
    var urlCenParts = urlParams.split("centerIdn=");
    var centerIdz = urlCenParts.length > 1 ? urlCenParts[1].split(/[?&]/)[0] : null;

    // Extract teaId from the URL
    var urlTeaParts = urlParams.split("teaIdn=");
    var teaIdz = urlTeaParts.length > 1 ? urlTeaParts[1].split("&")[0] : null;

    console.log("Center ID:", centerIdz);
    console.log("teacher ID:", teaIdz);
//End getting
//Fetch API
    function  fetchTeachers(centerIdz, teaIdz) {
      var URL = `/manager/teacher-detail/${teaIdz}/${centerIdz}`;
      console.log(URL);
      //get-center-name-in-fetch
      fetch(URL)
        .then((response) => {
            if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.json();
        })
        .then((data) => {
        console.log("Data received from API:", data[0]);
        document.getElementById("teacherNameHeading").innerText = "Thông tin cơ bản của " + data[0].teacherName;
        document.getElementById("genderTea").innerText = returnGender(data[0].teacherGender);
        document.getElementById("phoneTea").innerText = data[0].teacherPhone;
        document.getElementById("mailTea").innerText = data[0].teacherEmail;
        document.getElementById("addressTea").innerText = data[0].teacherAddress;
        document.getElementById("courseTea").innerText = data[0].courseNames;
        document.getElementById("feedbackTea").innerText = data[0].teacherEmail;
        })
        .catch((err) => console.error("loi lay teacher: ", err));
    }
     fetchTeachers(centerIdz, teaIdz);
    //Getting real gender
    function returnGender(g){
        if(g)   return "Nam";
        if(!g)  return "Nữ";
    }
//End fetching

//Date
    document.getElementById("currentDate").innerText = getCurrentDate();
    function getCurrentDate() {
            var currentDate = new Date();
            var dayOfWeek = [
              "Chủ Nhật",
              "Thứ Hai",
              "Thứ Ba",
              "Thứ Tư",
              "Thứ Năm",
              "Thứ Sáu",
              "Thứ Bảy",
            ];
            var day = String(currentDate.getDate()).padStart(2, "0");
            var month = String(currentDate.getMonth() + 1).padStart(2, "0");
            var year = currentDate.getFullYear();
            var dayIndex = currentDate.getDay();
            var dayName = dayOfWeek[dayIndex];
            return dayName + ", " + day + "-" + month + "-" + year;
          }
//End date
});

