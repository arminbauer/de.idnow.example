$ ->
      
  $("#listIdentifications").click ->
    $("#identifications").empty();
    $.get "/api/v1/pendingIdentifications", (identifications) ->
      $.each identifications, (index, ident) ->
        $("#identifications").append $("<li>").text(ident.id + " " + ident.name + " " + ident.time + " " + ident.waiting_time + " " + ident.companyid)
        
  $("#addTestCompany").click ->
    $.ajax "/api/v1/addCompany",
      type: "POST"
      contentType: "application/json"
      data: JSON.stringify({ "id": 333, "name": "Siemens", "sla_time": 60, "sla_percentage": 0.9, "current_sla_percentage": 0.8 })
      
  $("#addTestIdentification").click ->
    $.ajax "/api/v1/startIdentification",
      type: "POST"
      contentType: "application/json"
      data: JSON.stringify({ "id": 4711, "name": "Hans", "time": "1435637360", "waiting_time": 30, "companyid": 1 })
