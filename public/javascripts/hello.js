if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}

$( document ).ready(function() {

  $("#addCompany").click(function(){

      $("#selectCompany").append($('<option>', {
          value: 1,
          text: 'My option'
      }));

      $("#selectCompany").append(new Option("option text", "value"));

      $('#selectCompany')
               .append($("<option></option>")
               .attr("value",key)
               .text(value));

  });

  $("#showCompanies").click(function(){

    $.get( "/getCompanies", function( data ) {
      //$( ".result" ).html( data );
      //alert( "Load was performed." + data);

      $.each(data, function (i, item) {
          $('#selectCompany').append($('<option>', {
              value: item.id,
              text : item.name
          }));
          $('#companyList').append('<li>' + item.name + '</li>');

      });

    });


  });



});