document.addEventListener("DOMContentLoaded", function () {
    var boxInfot = document.getElementById("boxInfot");

    if (boxInfot) {
        boxInfot.addEventListener("click", function(event) {
            event.preventDefault();
                // Construct the correct URL based on centerId
                var url = `/manager/qlgv?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url); // Verify the constructed URL

                // Perform any further actions with the constructed URL
                window.location.href = url; // Example: Redirect to the constructed URL
        });
    } else {
        console.error("Element with id 'boxInfot' not found.");
    }
});