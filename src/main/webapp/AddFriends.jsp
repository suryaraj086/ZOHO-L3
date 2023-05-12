<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.expensemanagement.*"%>
<%@ page import="com.expensemanagement.datastore.*"%>
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="Css/Style.css">
      <title>Add new friends</title>
      <link rel = "icon" href = "Images/profile1.png" 
         type = "image/x-icon">
      <style type="text/css">
         #add,#remove {
         padding: 12px 20px;
         margin: 8px 0;
         display: inline-block;
         border: 1px solid #ccc;
         border-radius: 20px;
         box-sizing: border-box;
         background: #9999ff;
         color: white;
         cursor: pointer;
         transition-duration: 0.4s;
         text-decoration: none;
         font-family: sans-serif;
         font-size: 100%;
         cursor: pointer;
         }
         #add:disabled,#remove:disabled {
         border: 1px solid #999999;
         background-color: #cccccc;
         color: #666666;
         border-radius: 20px;
         box-sizing: border-box;
         padding: 12px 20px;
         margin: 8px 0;
         display: inline-block;
         }
      </style>
      <script src="Script/Script.js"></script>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script type="text/javascript">
         $(document).ready(function () {
          if(localStorage.getItem('dark')=="dark") {
         	document.body.classList.toggle('dark-mode');
              }
         });
      </script>
      <script>
         var paginate=0;
         var paginationValue=0;
         var paginatePrevious=0;
         var friends = new Array;
            $(document).ready(function() {
            	$("#add").on('click', function() {
            		$("input[name='friendslist']:checked").each(
            			    function(){
            			        friends.push($(this).attr('value'))  
            			       $("#"+$(this).attr('value')).remove();
                 }
            			);
            		if(friends.length==0)
            		{
            		  $('#message').text("select friends to add to friends list");
               $('#alert').css('display', 'block');
         	    setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
               return false;
            		}
            			$.ajax({
            			url : 'AddFriendsAction',
            			method : "POST",
            			data : JSON.stringify({
            				page:"Add to friends list",
            				friendslist:friends
            			}),
            			contentType: "application/json; charset=utf-8",
            			success : function(completeHtmlPage) {
            			    setTimeout(function(){location.reload();}, 1500);
            				var index=completeHtmlPage.indexOf("message");
            				var message=completeHtmlPage.substring(index+8);
          			      	  $('#message').text(message);
            			      $('#alert').css('display', 'block');
            			     if(message.indexOf("successfully")!=-1)
          			    	  {
          			    	  $('#alert').css('background-color', 'limegreen');
          			     	  }else{
          			    	  $('#alert').css('background-color', '#e9989fad');
          			    	  }
            			     
            			    setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
            			    friends=[];
            			    $('input[type=checkbox]').prop('checked',false);
            			    $('input[type=text]').each(function() {
            			        $(this).val('');
            			    });
            			},
            			error: function(XMLHttpRequest, textStatus, errorThrown) {
                    		location.reload();
                    	  }
            		});
            	});
            });
      </script>
      <script>
         $(document).ready(function() {
         $('#select-all').click(function(event) {   
             if(this.checked) {
                 $(':checkbox').each(function() {
                     this.checked = true;                        
                 });
             } else {
                 $(':checkbox').each(function() {
                     this.checked = false;                       
                 });
             }
         }); 
         });
         
         
      </script>
      <script>
         $(document).ready(function() {
         	$("#remove").on('click', function() {
         		$("input[name='friendslist']:checked").each(
         			    function(){
         			        friends.push($(this).attr('value')) 
         			        $("#"+$(this).attr('value')).remove();
         			        }
         			);
         		if(friends.length==0)
         		{
         		var message="select friends to remove";
         		  $('#message').text(message);
            $('#alert').css('display', 'block');
            setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
            return false;
         		}
         			$.ajax({
         			url : 'RemoveFriendsAction',
         			method : "POST",
         			data : JSON.stringify({
         				page:"remove from friends list",
         				friendslist:friends
         			}),
         			contentType: "application/json; charset=utf-8",
         			success : function(completeHtmlPage) {
         			    setTimeout(function(){location.reload();}, 1500);
         				var index=completeHtmlPage.indexOf("message");
         				var message=completeHtmlPage.substring(index+8);
         	      	  $('#message').text(message);
         			      $('#alert').css('display', 'block');
         			     if(message.indexOf("successfully")!=-1)
         		      {
         		    	 $('#alert').css('background-color', 'limegreen');
         		      }else{
         		    	  $('#alert').css('background-color', '#e9989fad');
         		      }
         		     setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
         		     friends=[];
         		     $('input[type=checkbox]').prop('checked',false);
         		     $('input[type=text]').each(function() {
         		         $(this).val('');
         		     });
         			},
         			error: function(XMLHttpRequest, textStatus, errorThrown) {
                 		location.reload();
                 	  }
         		});
         	});
         });
      </script>
      <script type="text/javascript">
         $(function () {
              $("#friendoption").change(function () {
                  var selectedText = $(this).find("option:selected").text();
                  var selectedValue = $(this).val();     
            $.ajax({
         	url : 'FriendsFilter',
         	method : "POST",
         	data : JSON.stringify({
         		page: selectedValue,
         	}),
         	contentType: "application/json; charset=utf-8",
         	success : function(data) {
         	var trHTML="";
            $.each(JSON.parse(data), function (key, val) {
            trHTML += '<tr id=\"'+val.userId+'\"><td><input type=\"checkbox\" name=\"friendslist\" value='+val.userId+' />&nbsp;</td><td>' + val.userId + '</td><td>' + val.name + '</td><td>' + val.emailId + '</td></tr>';
         });
         friends=[];
         if(trHTML=="")
         {
         trHTML='<tr><td>No data to display</td></tr>';	 
         }
         $("#customers").find("tr:gt(0)").remove();
         $('#customers').append(trHTML);
         $('input[type=checkbox]').prop('checked',false);
         $('input[type=text]').each(function() {
             $(this).val('');
         });
         if(selectedValue=="filter_friends") {
          	  $("#remove").prop("disabled", false);
          	  $("#add").prop("disabled", true);  
           	$('input[type=checkbox]').attr('disabled',false);
         
            } else if(selectedValue=="filter_not_friends"){
             $("#add").prop("disabled", false);
          	  $("#remove").prop("disabled", true);
           	$('input[type=checkbox]').attr('disabled',false);
         
            }else{
                $("#add").prop("disabled", true);
             	 $("#remove").prop("disabled", true);    
             	$('input[type=checkbox]').attr('disabled',true);
         	}
         	},
         	 error: function(XMLHttpRequest, textStatus, errorThrown) {
         		location.reload();
         	  }
         	
         });
         });
         });
      </script>
      <script type="text/javascript">
         function pagination(value) {
         
         $(".pagination_buttonPrevious").show();
         
          paginate++;
          $('input[type=text]').each(function() {
              $(this).val('');
          });
         $.ajax({
         url : 'FriendsFilter',
         method : "POST",
         data : JSON.stringify({
         page:"pagination",
         pageNo:paginate+"",
         filter:$("#friendoption").val(),
         }),
         contentType: "application/json; charset=utf-8",
         success : function(data) {
         var trHTML="";
              $.each(JSON.parse(data), function (key, val) {
                  trHTML += '<tr><td><input type=\"checkbox\" name=\"friendslist\" value='+val.userId+' />&nbsp;</td><td>' + val.userId + '</td><td>' + val.name + '</td><td>' + val.emailId + '</td></tr>';
              });
              if(trHTML=="")
         {
         trHTML='<tr><td>No data to display</td></tr>';	 
         }
              $("#customers").find("tr:gt(0)").remove();
              $('#customers').append(trHTML);
              $('#myPager').empty();
              trHTML="";
              trHTML='<li><button name=\"pagination_button\" class=\"pagination_buttonPrevious\" style=\"display:block;\" onclick=\"paginationPrevious(this.value)\"; value='+paginatePrevious+'><<</button></li>';
            	 trHTML+='<li><button>'+(paginate+1)+"-"+(paginationValue)+'</button></li>';
            	 trHTML+='<li><button name=\"pagination_button\" class=\"pagination_button\"  onclick=\"pagination(this.value)\"; value='+paginate+'>>></button></li>';
                 $('#myPager').append(trHTML);
           $('input[type=checkbox]').prop('checked',false);
         if($("#friendoption").val()=="filter_all"){ 
                  	$('input[type=checkbox]').attr('disabled',true);
              	}
              if(paginationValue==paginate+1)
         	  {
         	   $(".pagination_button").hide();
         	  return false;
         	  }
         $(".pagination_button").show();
         
         },
         error: function(XMLHttpRequest, textStatus, errorThrown) {
            		location.reload();
            	  }
         });
         }
         
         
         function paginationPrevious(value) {
         $(".pagination_button").show();
         
          paginate--;
          $('input[type=text]').each(function() {
              $(this).val('');
          });
         $.ajax({
         url : 'FriendsFilter',
         method : "POST",
         data : JSON.stringify({
         page:"pagination",
         pageNo:paginate+"",
         filter:$("#friendoption").val(),
         }),
         contentType: "application/json; charset=utf-8",
         success : function(data) {
         var trHTML="";
              $.each(JSON.parse(data), function (key, val) {
                  trHTML += '<tr><td><input type=\"checkbox\" name=\"friendslist\" value='+val.userId+' />&nbsp;</td><td>' + val.userId + '</td><td>' + val.name + '</td><td>' + val.emailId + '</td></tr>';
              });
              if(trHTML=="")
         {
         trHTML='<tr><td>No data to display</td></tr>';	 
         }
              $("#customers").find("tr:gt(0)").remove();
              $('#customers').append(trHTML);
              $('#myPager').empty();
              trHTML='<li><button name=\"pagination_button\" class=\"pagination_buttonPrevious\" style=\"display:none;\" onclick=\"paginationPrevious(this.value)\"; value='+paginatePrevious+'><<</button></li>';
            	 trHTML+='<li><button>'+(paginate+1)+"-"+(paginationValue)+'</button></li>';
            	 trHTML+='<li><button name=\"pagination_button\" class=\"pagination_button\"  onclick=\"pagination(this.value)\"; value='+paginate+'>>></button></li>';
                 $('#myPager').append(trHTML);
           $('input[type=checkbox]').prop('checked',false);
           if($("#friendoption").val()=="filter_all"){ 
                  	$('input[type=checkbox]').attr('disabled',true);
              	}
              if(paginate==0)
         	 {
               $(".pagination_buttonPrevious").hide();
         	 return false;
         	 }
           $(".pagination_buttonPrevious").show();
         },
         error: function(XMLHttpRequest, textStatus, errorThrown) {
            		location.reload();
            	  }
         });
         }
      </script>
      <script>
         $(document).ready(function() {	 
         var selectedValue = "filter_all";
         $.ajax({
         url : 'FriendsFilter',
         method : "POST",
         data : JSON.stringify({
         page: selectedValue,
         }),
         contentType: "application/json; charset=utf-8",
         success : function(data) {
         var trHTML="";
         $.each(JSON.parse(data), function (key, val) {
             trHTML += '<tr><td><input type=\"checkbox\" name=\"friendslist\" value='+val.userId+' />&nbsp;</td><td>' + val.userId + '</td><td>' + val.name + '</td><td>' + val.emailId + '</td></tr>';
         });
         friends=[];
         if(trHTML=="")
         {
         trHTML='<tr><td>No data to display</td></tr>';	 
         }
         $("#customers").find("tr:gt(0)").remove();
         $('#customers').append(trHTML);
         $('input[type=checkbox]').attr('disabled',true);
         }
         });
         });
         
      </script>
      <script>
         $(document).ready(function() {   	 
         $.ajax({
         url : 'FriendsFilter',
         method : "POST",
         data : JSON.stringify({
         page: "get_total_pages",
         filter:$("#friendoption").val(),
         }),
         contentType: "application/json; charset=utf-8",
         success : function(data) {
         var trHTML="";
         $.each(JSON.parse(data), function (key, val) {
          paginationValue=val;
          if(val>1)
         	{
          trHTML='<li><button name=\"pagination_button\" class=\"pagination_buttonPrevious\" style=\"display:none;\" onclick=\"paginationPrevious(this.value)\"; value='+paginatePrevious+'><<</button></li>';
          trHTML+='<li><button>'+(paginate+1)+"-"+(paginationValue)+'</button></li>';
          trHTML+='<li><button name=\"pagination_button\" class=\"pagination_button\"  onclick=\"pagination(this.value)\"; value='+paginate+'>>></button></li>';
         	}
         });
         friends=[];
         $('#myPager').append(trHTML);
         }
         });
         });
         
      </script>
      <script>
         $(document).ready(function() {  
         $("#friendoption").change(function() {
         paginate=0;
         $.ajax({
         url : 'FriendsFilter',
         method : "POST",
         data : JSON.stringify({
         page: "get_total_pages",
         filter: $("#friendoption").val(),
         }),
         contentType: "application/json; charset=utf-8",
         success : function(data) {
         $('#myPager').empty();
         var trHTML="";
         var temp="";
         $.each(JSON.parse(data), function (key, val) {
          paginationValue=val;
          if(val>1)
         {
          temp='<li><button name=\"pagination_button\" class=\"pagination_buttonPrevious\" style=\"display:none;\" onclick=\"paginationPrevious(this.value)\"; value='+paginatePrevious+'><<</button></li>';
          temp+='<li><button>'+(paginate+1)+"-"+(paginationValue)+'</button></li>';
          temp+='<li><button name=\"pagination_button\" class=\"pagination_button\"  onclick=\"pagination(this.value)\"; value='+paginate+'>>></button></li>';
         }
         });
         friends=[];
         $('#myPager').append(temp);
         },error: function(XMLHttpRequest, textStatus, errorThrown) {
         location.reload();
         }
         });
         });
         });
         
      </script>
   </head>
   <body>
      <%
         if (session.getAttribute("userId") == null) {
         	response.sendRedirect(request.getContextPath() + "/Login.jsp");
         	return;
         }
         %>
      <jsp:include page="Menu.jsp"></jsp:include>
      <div class="alert" id="alert" style="display: none;"> <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> <strong id="message"></strong> </div>
      <br/>
      <br/>
      <br/>
      <h1>User Table</h1>
      <div  id="userform">
         <button name="addFriends" id="add" disabled="disabled">Add to friends list</button> 
         <button name="removefriends" id="remove"  disabled="disabled">Remove from friends list</button>
         <select class="friendoption" id="friendoption">
            <option value="filter_all">All</option>
            <option value="filter_friends">Friends List</option>
            <option value="filter_not_friends">Except friends List</option>
         </select>
         <input type="text" onkeypress="return /[0-9]/i.test(event.key)"  style="width: auto;" id="myInput" onkeyup="userIdFilter()"
            placeholder="Type to search id">
         <input type="text" style="width: auto;" id="myInput1" onkeyup="userNameFilter()"
            placeholder="Type to search name">
         <input type="text" style="width: auto; display: inline;" id="myInput2" onkeyup="userPhoneFilter()"
            placeholder="Type to search email address">
         <table id="customers"  class="pagination" data-pagecount="3" style="width: 100%">
            <tr>
               <th><input type="checkbox" id="select-all"  value="" />&nbsp;Select</th>
               <th>User Id</th>
               <th>User Name</th>
               <th>Email id</th>
            </tr>
            <tbody id="myTable" ></tbody>
         </table>
         <br/>
         <div>
            <ul id="myPager"></ul>
         </div>
      </div>
   </body>
</html>