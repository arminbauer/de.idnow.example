$ ->
  $.get "/api/v1/getCompanies", (companies) ->
    $.each companies, (index, company) ->
      $("#getCompanies").append $("<li>").text (company.id + " " + company.name + " " + company.sla_time + " " + company.sla_percentage + " " + company.current_sla_percentage)

  $.get "/api/v1/getIdentifications", (identifications) ->
    $.each identifications, (index, identification) ->
      $("#getIdentifications").append $("<li>").text (identification.id + " " + identification.name + " " + identification.companyid + " " + identification.time + " " + identification.waiting_time)