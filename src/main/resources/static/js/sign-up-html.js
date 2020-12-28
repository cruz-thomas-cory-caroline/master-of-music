(function () {
    "use strict";



$(document).ready(function() {

    $("#pwRequirementsList").hide();
    $("#pwRequirements").click(function () {
        $("#pwRequirementsList").toggle();
    });

    $("#passwordStrength").hide();
    $("#passwordEntered").keyup(function () {
        $("#passwordStrength").show();
    })

});

})();