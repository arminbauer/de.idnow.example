(function() {

    if (window.JSON && window.atob && document.querySelectorAll && document.querySelector) {
        fetchIdentifications();
    }

    function fetchIdentifications() {
        var request = new Request("/api/v1/identifications");

        fetch(request).then(function (response) {
            if (response.status == 200) return response.json();
            else throw new Error("Error requesting list of identifications");
        }).then(function(response) {
            if (window.console) {
                console.debug(response);
            }
            refreshIdentifications(response);
        }).catch(function(error) {
            if (window.console) {
                console.error(error);
            }
        });
    }

    function refreshIdentifications(identifications) {
        var node = document.querySelector("#identifications");

        var json = "";

        identifications.forEach(function(identification) {
            json += "\n" + JSON.stringify(identification);
        })

        node.innerHTML = "<pre>" + json + "</pre>";
    }
})();