package com.apoorva.echomark

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater

class Sessions(
    var id: Int,
    var user_id: Int,
    var created_at: String,
    var updated_at: String,
    var key:  String,
    var url:  String

)