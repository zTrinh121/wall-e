document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.float-gallery-content button').forEach(button => {
        button.addEventListener('click', function () {
            const courseTitle = this.closest('li').querySelector('.main-content h3').innerText;
            const courseDescription = this.closest('li').querySelector('.main-content p span').innerText;
            const coursePrice = 150000; // Set the course price here
            const courseId = this.closest('li').getAttribute('data-courseid'); // Lấy courseId từ data-courseid
            document.getElementById('modal-title').innerText = courseTitle;
            document.getElementById('modal-description').innerText = courseDescription;
            document.getElementById('modal-price').innerText = coursePrice;

            const modal = UIkit.modal('#modal-example');
            modal.show();

            document.getElementById('registerBtn').addEventListener('click', function () {
                // Save courseId to session
                console.log(courseId)
                fetch('/api/v1/payment/courseId', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ courseId: parseInt(courseId)  })

                })
                    .then(response => {
                        if (!response.ok) {

                            throw new Error('Failed to save courseId to session');
                        }
                        console.log(JSON.stringify({ courseId: parseInt(courseId)  }))
                        return response.json();
                    })
                    .then(data => {
                        console.log('CourseId saved to session:', data);
                        fetch('api/v1/payment/auth/status')
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error('Network response was not ok');
                                }
                                return response.json();
                            })
                            .then(isAuthenticated => {
                                if (isAuthenticated) {
                                    const paymentUrlEndpoint = `http://localhost:8080/api/v1/payment/vn-pay?amount=${coursePrice}&bankCode=NCB&courseId=${courseId}`;

                                    fetch(paymentUrlEndpoint)
                                        .then(response => {
                                            if (!response.ok) {
                                                throw new Error('Failed to retrieve payment URL');
                                            }
                                            return response.json();
                                        })
                                        .then(data => {
                                            console.log(data);
                                            const paymentUrl = data.data.paymentUrl;
                                            console.log(paymentUrl);
                                            if (paymentUrl) {
                                                // Redirect to the paymentUrl
                                                window.location.href = paymentUrl;
                                            } else {
                                                console.error('Payment URL is missing in response');
                                                // Handle error scenario
                                            }
                                        })
                                        .catch(error => {
                                            console.error('Error fetching payment URL:', error);
                                            // Handle fetch error
                                        });
                                } else {
                                    // Redirect to login if not authenticated
                                    window.location.href = '/login';
                                }
                            })
                            .catch(error => {
                                console.error('Error checking authentication status:', error);
                                // Handle fetch error or authentication error
                            });
                    })
                    .catch(error => {
                        console.error('Error saving courseId to session:', error);
                        // Handle error
                    });
            });
        });
    });
});
