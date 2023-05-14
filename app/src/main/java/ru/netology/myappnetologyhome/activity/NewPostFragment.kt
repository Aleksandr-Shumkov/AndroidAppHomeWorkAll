package ru.netology.myappnetologyhome.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myappnetologyhome.databinding.FragmentNewPostBinding
import ru.netology.myappnetologyhome.utils.AndroidUtils
import ru.netology.myappnetologyhome.utils.TextArg
import ru.netology.myappnetologyhome.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()

    companion object {
        var Bundle.textArg: String? by TextArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(layoutInflater)


        arguments?.textArg?.let(binding.content::setText)

        if(binding.content.text.isNullOrBlank()){
            val draft = viewModel.getDraft()
            if (!draft.isNullOrBlank()) {
                viewModel.changeContent(draft)
                viewModel.saveDraft(draft)
                binding.content.setText(draft)
            }
        }

        binding.content.addTextChangedListener{
            if (viewModel.edited.value?.id == 0L) {
                viewModel.changeContent(binding.content.text.toString())
                viewModel.saveDraft(binding.content.text.toString())
            }
        }

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