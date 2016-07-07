<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="currentPage" value="${param.currentPage}" />
<c:set var="currentLimit" value="${param.currentLimit}" />
<c:set var="nameSearched" value="${param.nameSearched}" />
<c:set var="nameSearched" value="${param.nameSearched}" />
<c:set var="totalComputers" value="${param.totalComputers}" />

<!-- Modal -->
<div class="modal fade" id="import_export_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Export</h4>
      </div>
      <div class="modal-body">
      
      	<div class="row" style="margin: 0 5px 20px 5px; text-align: center;">
      		<div class="col-xs-6">
        		Export the ${totalComputers} computers selected:
        	</div>
      		<div class="col-xs-6">
        		<a class="btn btn-default" href="Export?page=${currentPage}&limit=${currentLimit}&search=${nameSearched}">Export</a>
        	</div>
		</div>
	</div>
	<div class="modal-header">
		<h4 class="modal-title" id="myModalLabel">Import</h4>
	</div>
	<div class="modal-body">
		<div class="row" style="margin: 0 5px 0 5px; text-align: center;">				
			<form method="POST" action="Import" enctype="multipart/form-data">
				<div class="col-xs-6">
					<input type="file" name="file">
				</div>
				<div class="col-xs-6">
					<input class="btn btn-default" type="submit" value="Import">
				</div>
			</form>	
		</div>
		<!-- i18n -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>