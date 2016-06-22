$(function() {
    $('script[type="text/html"]').each(function() {
        var html = $(this).html();
        $(document.body).append(html);
    });
});
