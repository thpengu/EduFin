package uz.texnopos.texnoposedufinance.data.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.texnopos.texnoposedufinance.data.model.*
import uz.texnopos.texnoposedufinance.data.model.response.*

interface ApiInterface {
    @GET("getAllCourses")
    fun getAllCourses(@Query("id") userId: String): Call<List<Course>>

    @GET("getGroupParticipants")
    fun getGroupParticipants(
        @Query("orgId") orgId: String,
        @Query("groupId") id: String
    ): Call<List<ParticipantResponse>>

    @GET("selectExistingStudentToGroup")
    fun selectExistingStudentToGroup(
        @Query("orgId") orgId: String,
        @Query("groupId") groupId: String
    ): Call<List<Student>>

    @POST("createParticipantIfStudentNotExists")
    fun createParticipantIfStudentNotExists(@Body data: CreateParticipantRequest): Call<PostResponse>

    @POST("coursePayment")
    fun coursePayment(@Body data: CoursePayments): Call<PostResponse>

    @POST("createParticipantWithNewStudent")
    fun createParticipantWithNewStudent(@Body data: CreateParticipantRequest): Call<PostResponse>

    @POST("createParticipantWithStudentId")
    fun createParticipantWithStudentId(@Body data: SendParticipantDataRequest): Call<PostResponse>

    @POST("checkContract")
    fun checkContract(@Body data: ContractRequest): Call<PostResponse>

    @GET("report")
    fun getReports(
        @Query("orgId") orgId: String,
        @Query("fromDate") fromDate: Long,
        @Query("toDate") toDate: Long
    ): Call<ReportResponse>

    @POST("addExpense")
    fun addExpense(@Body data: ExpenseRequest): Call<ExpenseRequest>

    @POST("addIncome")
    fun addIncome(@Body data: IncomeRequest): Call<IncomeRequest>

    @GET("salary")
    fun getSalary(
        @Query("orgId") orgId: String,
        @Query("fromDate") fromDate: Long,
        @Query("toDate") toDate: Long
    ): Call<SalaryResponse>
}