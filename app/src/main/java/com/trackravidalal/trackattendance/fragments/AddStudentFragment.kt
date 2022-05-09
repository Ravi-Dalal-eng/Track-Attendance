package com.trackravidalal.trackattendance.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.databinding.FragmentAddStudentBinding
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.utils.Converters
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding?=null
    private val binding get()=_binding!!
    private val pickimage=555
    private val pickimagecode=222
    private lateinit var shareViewModel: OurViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentAddStudentBinding.bind(view)
        val ourDatabase= OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        shareViewModel= ViewModelProvider(requireActivity(), OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)


        binding.studentProfileImage.setOnClickListener {
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
              if(requireActivity().checkSelfPermission
                      (Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), pickimage)
              }
              else{
            pickTheImageFromGallery()
              }
            }
            else{
               pickTheImageFromGallery()
            }
        }

        binding.addStudent.setOnClickListener {
            if (binding.firstName.text.toString().isEmpty()) {
                binding.firstNameTextField.isErrorEnabled = true
                binding.firstNameTextField.error = "First name is required"
                return@setOnClickListener
            }
            else
                binding.firstNameTextField.isErrorEnabled = false

            if (binding.lastName.text.toString().isEmpty()) {
                binding.lastNameTextField.isErrorEnabled = true
                binding.lastNameTextField.error = "Last name is required"
                return@setOnClickListener
            }
            else
                binding.lastNameTextField.isErrorEnabled = false

            if (binding.rollno.text.toString().isEmpty()) {
                binding.rollnoTextField.isErrorEnabled = true
                binding.rollnoTextField.error = "Roll No. is required"
                return@setOnClickListener
            }
            else
                binding.rollnoTextField.isErrorEnabled = false

            if (binding.dateOfBirth.text.toString().isEmpty()) {
                binding.dateOfBirthTextField.isErrorEnabled = true
                binding.dateOfBirthTextField.error = "Date of birth is required"
                return@setOnClickListener
            }
            else
                binding.dateOfBirthTextField.isErrorEnabled = false

            if (binding.email.text.toString().isEmpty()) {
                binding.emailTextField.isErrorEnabled = true
                binding.emailTextField.error = "Email is required"
                return@setOnClickListener
            }
            else
                binding.emailTextField.isErrorEnabled = false

            if (binding.mobileNo.text.toString().isEmpty()) {
                binding.mobileNoTextField.isErrorEnabled = true
                binding.mobileNoTextField.error = "Mobile No. is required"
                return@setOnClickListener
            }
            else
                binding.mobileNoTextField.isErrorEnabled = false

            var student: Student? = null
            val converters = Converters()
            if (binding.studentProfileImage.tag == "unset")
                student = Student(
                    0,
                    binding.firstNameTextField.editText!!.text.toString(),
                    binding.lastNameTextField.editText!!.text.toString(),
                    binding.mobileNoTextField.editText!!.text.toString(),
                    binding.rollnoTextField.editText!!.text.toString(),
                    binding.emailTextField.editText!!.text.toString(),
                    binding.dateOfBirthTextField.editText!!.text.toString(),
                    null
                )
            else
                student = Student(
                    0,
                    binding.firstNameTextField.editText!!.text.toString(),
                    binding.lastNameTextField.editText!!.text.toString(),
                    binding.mobileNoTextField.editText!!.text.toString(),
                    binding.rollnoTextField.editText!!.text.toString(),
                    binding.emailTextField.editText!!.text.toString(),
                    binding.dateOfBirthTextField.editText!!.text.toString(),
                    converters.fromBitmap(
                        binding.studentProfileImage.drawable.toBitmap(
                            binding.studentProfileImage.drawable.intrinsicWidth,
                            binding.studentProfileImage.drawable.intrinsicHeight,
                            Bitmap.Config.ARGB_8888
                        )
                    )
                )

            GlobalScope.launch(Dispatchers.IO) {
                shareViewModel.addStudent(student)
            }

                Snackbar.make(
                    requireActivity().findViewById(R.id.drawer_layout),
                    "Student added successfully...",
                    Snackbar.LENGTH_LONG
                ).show()
                findNavController().popBackStack()
            }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if(requestCode==pickimage){
        if (grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            pickTheImageFromGallery()
        }
        else
        {
            Toast.makeText(requireContext(), " Please allow the Permission to select image", Toast.LENGTH_SHORT).show() }
    }
   }
    fun pickTheImageFromGallery(){
    val intent= Intent(Intent.ACTION_GET_CONTENT)
    intent.setType("image/*")
    startActivityForResult(Intent.createChooser(intent, "Select an image..."), pickimagecode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == pickimagecode && data != null) {
            binding.studentProfileImage.setImageURI(data.data)
            binding.studentProfileImage.tag="set"
        }

    }
}