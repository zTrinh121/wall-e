document.addEventListener("DOMContentLoaded", function () {
    const userId = document.getElementById("userId").innerText;
    const userRole = document.getElementById("userRole").innerText;
    const urlParams = new URLSearchParams(window.location.search);
    const notificationId = parseInt(urlParams.get('id'), 10);
    let apiNotificationUrlGet;

    switch (userRole){
        case "PARENT":
            apiNotificationUrlGet = `api/parent/notifications/all`;
            break;
        case "STUDENT":
            apiNotificationUrlGet = `api/student/notifications/all`;
            break;
        case "TEACHER":
            apiNotificationUrlGet = `api/teacher/notifications/all`;
            break;
    }

    function fetchNotifications(url) {
        fetch(url)
            .then(response => response.json())
            .then(data => {
                let allNotifications = [];
                allNotifications.push(...data.individualNotifications);
                if (userRole !== "PARENT") {
                    allNotifications.push(...data.centerNotifications);
                }
                allNotifications.push(...data.systemNotifications);
                allNotifications.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

                // Find the notification with the matching ID
                const notification = allNotifications.find(n => n.id === notificationId);
                if (notification) {
                    displayNotificationDetails(notification);
                } else {
                    console.error('Notification not found');
                }
            })
            .catch(error => console.error('Error fetching notifications:', error));
    }

    function displayNotificationDetails(notification) {
        console.log(notification.title)
        const notificationTitle = document.getElementById('notificationTitle');
        const notificationContent = document.getElementById('notificationContent');
        notificationTitle.innerText = notification.title;
        notificationContent.innerText = notification.content;
    }



    fetchNotifications(apiNotificationUrlGet);
});
