//article delete with confirmation
$(document).ready(function () {
    $('.delete-btn').click(function () {
        const articleId = $(this).data('article-id');
        const deleteUrl = '/article/delete/' + articleId;
        $('#confirmDelete').attr('data-delete-url', deleteUrl);
        $('#deleteConfirmationModal').modal('show');
    });
    $('#confirmDelete').click(function () {
        window.location.href = $(this).data('delete-url');
    });
});
