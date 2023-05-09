package ru.netology.myappnetologyhome.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myappnetologyhome.databinding.FragmentNewPostBinding
import ru.netology.myappnetologyhome.utils.AndroidUtils
import ru.netology.myappnetologyhome.utils.TextArg
import ru.netology.myappnetologyhome.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by TextArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by activityViewModels()

        arguments?.textArg?.let(binding.content::setText)

        binding.ok.setOnClickListener {

            if (!binding.content.text.isNullOrBlank()) {
                val text = binding.content.text.toString()
                viewModel.changeContent(text)
                viewModel.save()
                AndroidUtils.hideKeyboard(requireView())
            }
            findNavController().navigateUp()
        }

        return binding.root
    }

}