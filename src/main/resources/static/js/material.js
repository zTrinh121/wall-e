    function displayButtons() {
        const gradeButtons = document.querySelector('.button');
        const subjectButtons = document.getElementById('subject-buttons');

        // Hide grade buttons
        gradeButtons.style.display = 'none';

        // Show subject buttons
        subjectButtons.style.display = 'flex';
        subjectButtons.innerHTML = ''; // Clear previous content

        // List of subjects to display
        const subjects = [
            "Toán", "Lý", "Hóa", "Sinh học", "Văn", "Sử", "Địa lý", "Giáo dục công dân",
            "Công nghệ", "Tiếng Anh", "Tiếng Pháp", "Tiếng Nhật", "Thể dục", "Âm nhạc",
            "Mỹ thuật", "GDQP-AN", "Tin học", "Kỹ năng sống", "Khác"
        ];

        // Create buttons for each subject
        subjects.forEach(subject => {
            const buttonItem = document.createElement('div');
            buttonItem.className = 'button-item';

            const button = document.createElement('button');
            button.textContent = subject;
            button.onclick = () => redirectToSubject(subject);

            buttonItem.appendChild(button);
            subjectButtons.appendChild(buttonItem);
        });
    }

    function redirectTo(grade) {
        window.location.href = `material?grade=${grade}`;
    }

    function redirectToSubject(subject) {
        // Handle redirection to the subject page or do any other action
        alert(`Redirect to subject: ${subject}`);
    }


    document.addEventListener("DOMContentLoaded", () => {
        const roleUser = document.getElementById("roleUser").innerHTML;
        const uploadtn = document.getElementById("upload-btn");
        console.log(uploadtn)

        //Start: add material btn
        // switch (roleUser){
        //     case "STUDENT":
        //         uploadtn.style.display = "none";
        //         break;
        //     case "PARENT":
        //         uploadtn.style.display = "none";
        //         break;
        // }
        //End: add material btn
    })