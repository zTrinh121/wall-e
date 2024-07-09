document.addEventListener("DOMContentLoaded", function () {
//Get information from URL
    // Get the current URL
    var urlParams = window.location.href;
    console.log("Current URL:", urlParams);

    // Extract centerId from the URL
    var urlCenParts = urlParams.split("centerIdn=");
    var centerIdz = urlCenParts.length > 1 ? urlCenParts[1].split(/[?&]/)[0] : null;

    // Extract stuId from the URL
    var urlStuParts = urlParams.split("stuIdn=");
    var stuIdz = urlStuParts.length > 1 ? urlStuParts[1].split("&")[0] : null;

    console.log("Center ID:", centerIdz);
    console.log("Student ID:", stuIdz);
//End getting
//turnBack
    var turnBack = document.getElementById("turnBack");

    turnBack.addEventListener("click", function(event) {
        event.preventDefault();
            var url = `/manager/qlhv?centerId=`;
            url += encodeURIComponent(centerIdz);
            window.location.href = url;
    });
//Ending
//Fetch API
    function fetchStudents(centerIdz, stuIdz) {
      var URL = `/manager/student-detail/${stuIdz}/${centerIdz}`;
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
        document.getElementById("studentNameHeading").innerText = "Thông tin cơ bản của " + data[0].studentName;
        document.getElementById("nameParent").innerText = "Phụ huynh " + data[0].parentName;
        document.getElementById("genderStu").innerText = returnGender(data[0].studentGender);
        document.getElementById("genderP").innerText = returnGender(data[0].parentGender);
        document.getElementById("phoneStu").innerText = data[0].studentPhone;
        document.getElementById("phoneP").innerText = data[0].parentPhone;
        document.getElementById("mailStu").innerText = data[0].studentEmail;
        document.getElementById("mailP").innerText = data[0].parentEmail;
        document.getElementById("addressStu").innerText = data[0].studentAddress;
        document.getElementById("courseStu").innerText = data[0].courseNames;
        document.getElementById("feedbackStu").innerText = data[0].studentEmail;
        })
        .catch((err) => console.error("loi lay student: ", err));
    }
    fetchStudents(centerIdz, stuIdz);
    //Geting real gender
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

