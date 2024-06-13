// Start attendance details
function showAttendanceModal() {
  const modal = document.getElementById("attendanceModal");
  modal.style.display = "block";
  populateAttendanceModal();
}

function closeAttendanceModal() {
  const modal = document.getElementById("attendanceModal");
  modal.style.display = "none";
}

function populateAttendanceModal() {
  const attendanceList = document.getElementById("attendanceList");
  attendanceList.innerHTML = ""; // Xóa nội dung cũ (nếu có)

  const attendanceData = [
      { session: 1, status: "present" },
      { session: 2, status: "absent" },
      // Thêm dữ liệu cho các buổi khác tùy theo nhu cầu
  ];

  attendanceData.forEach(item => {
      const listItem = document.createElement("div");
      listItem.textContent = `Buổi ${item.session}: ${item.status === "present" ? "Điểm danh" : "Vắng mặt"}`;
      listItem.style.color = item.status === "present" ? "green" : "red";
      attendanceList.appendChild(listItem);
  });
}
//End attendance detais

// Hiển thị modal điểm kiểm tra
function showEvaluationModal() {
  const modal = document.getElementById("evaluationModal");
  modal.style.display = "block";
}

// Đóng modal điểm kiểm tra
function closeEvaluationModal() {
  const modal = document.getElementById("evaluationModal");
  modal.style.display = "none";
}

// Danh sách lớp học (mẫu)
const classListData = [
  { id: 1, name: "Nguyễn Văn A" },
  { id: 2, name: "Trần Thị B" },
  { id: 3, name: "Lê Văn C" },
  // Thêm các học sinh khác tương tự
];

// Hiển thị modal danh sách lớp
function showClassListModal() {
  const modal = document.getElementById("classListModal");
  const classListBody = document.getElementById("classListBody");
  // Xóa bỏ các dòng cũ trong bảng
  classListBody.innerHTML = "";
  console.log(classListBody)
  // Thêm các học sinh vào bảng
  classListData.forEach((student, index) => {
      const row = document.createElement("tr");
      row.innerHTML = `
          <td>${index + 1}</td>
          <td>${student.name}</td>
      `;
      classListBody.appendChild(row);
  });
  modal.style.display = "block";
}

// Đóng modal danh sách lớp
function closeClassListModal() {
  const modal = document.getElementById("classListModal");
  modal.style.display = "none";
}
