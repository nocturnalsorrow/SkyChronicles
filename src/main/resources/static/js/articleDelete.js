//article delete with confirmation
$(document).ready(function () {
    $('.delete-btn').click(function () {
        var articleId = $(this).data('article-id');
        var deleteUrl = '/article/delete/' + articleId;
        $('#confirmDelete').attr('data-delete-url', deleteUrl);
        $('#deleteConfirmationModal').modal('show');
    });
    $('#confirmDelete').click(function () {
        var deleteUrl = $(this).data('delete-url');
        window.location.href = deleteUrl;
    });
});
