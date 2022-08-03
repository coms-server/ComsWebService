var checkedRows = [];
$(document).ready(function() {
    $("#modalTable").on("shown.bs.modal", function() {
        $(this).find("#table").bootstrapTable("resetView");
        $(this).find("#table").bootstrapTable('uncheckAll');
    });

    $("#modalTable").on("hidden.bs.modal", function() {
        updateList();
    });

    $("#modalTable").on("check.bs.table", function(e, row) {
        checkedRows.push({
            user_idx: row.user_idx,
            name: row.term + "기 " + $(row.name).text()
        });
    });
    $("#modalTable").on("uncheck.bs.table", function(e, row) {
        $(checkedRows).each(function(index, value) {
            if (value.user_idx === row.user_idx) {
                checkedRows.splice(index, 1);
            }
        });
    });

    $("#modalTable").on("check-all.bs.table uncheck-all.bs.table", function(e, rowsAfter, rowsBefore) {
        checkedRows = [];
        $(rowsAfter).each(function(index, value) {
            checkedRows.push({
                user_idx: value.user_idx,
                name: value.term + "기 " + $(value.name).text()
            });
        })
    });

    $("#modalTable #close").on("click", function() {
        $(this).parents("#modalTable").modal("hide");
    })
});

function updateList() {
    var userList = [];
    var userNames = [];
    $(checkedRows).each(function() {
        userList.push(this.user_idx);
        userNames.push(this.name);
    });
    $(".userList").each(function() {
        $(this).val(JSON.stringify(userList));
    })
    addUserToList(userNames);
}

function addUserToList(list) {
    var target = $(".userNames");

    $(target).each(function() {
        var nameList = $(this);

        $(nameList).empty();
        $(list).each(function(index, value) {
            var label = document.createElement("label");
            label.innerHTML = value;
            $(nameList).append(label);
        });
    })
}