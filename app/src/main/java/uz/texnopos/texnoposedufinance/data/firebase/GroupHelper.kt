package uz.texnopos.texnoposedufinance.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import uz.texnopos.texnoposedufinance.data.model.Group
import java.util.*

class GroupHelper(auth: FirebaseAuth, private val db: FirebaseFirestore, private val func: FirebaseFunctions) {
    private val orgId = auth.currentUser!!.uid

    fun createGroup(name: String,
                    teacher: String,
                    courseId: String,
                    time: String,
                    startDate: String,
                    onSuccess: () -> Unit,
                    onFailure: (msg: String?) -> Unit
    ){
        val id = UUID.randomUUID().toString()
        val newGroup = Group(
            id = id,
            orgId = orgId,
            courseId = courseId,
            name = name,
            teacher = teacher,
            time = time,
            startDate = startDate)
        db.collection("users/$orgId/groups").document(id).set(newGroup)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getAllGroups(
        courseId: String,
        onSuccess: (list: List<Group>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ){
        db.collection("users/$orgId/groups").get()
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}