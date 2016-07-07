<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="currentPage" value="${param.currentPage}" />
<c:set var="currentLimit" value="${param.currentLimit}" />
<c:set var="nameSearched" value="${param.nameSearched}" />

<!-- Modal -->
<div class="modal fade" id="import_export_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
      </div>
      <div class="modal-body">
      
      	<div class="row">
      		<div>
        		<a class="btn btn-default" href="Export?page=${currentPage}&limit=${currentLimit}&search=${nameSearched}">Export</a>
        	</div>
		</div>
		<div class="row">
			<div class="btn-group btn-group-sm pull-left" role="group">					
				<form method="POST" action="Import" enctype="multipart/form-data">
					<input type="file" name="file">
					<input class="btn btn-default" style="display: inline-block;" type="submit" value="Import">
				</form>				
			</div>
		</div>
		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>