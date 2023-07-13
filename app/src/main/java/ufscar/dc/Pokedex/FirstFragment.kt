package ufscar.dc.Pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import ufscar.dc.Pokedex.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment() : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val btn2 = requireActivity().findViewById<Button>(R.id.button_pokedex_completa)
        val btn3 = requireActivity().findViewById<Button>(R.id.button_pokemons_capturados)
        btn2.visibility = View.INVISIBLE
        btn3.visibility = View.INVISIBLE


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            val btn2 = requireActivity().findViewById<Button>(R.id.button_pokedex_completa)
            val btn3 = requireActivity().findViewById<Button>(R.id.button_pokemons_capturados)
            btn2.visibility = View.VISIBLE
            btn3.visibility = View.VISIBLE
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}