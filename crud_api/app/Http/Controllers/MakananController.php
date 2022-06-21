<?php

namespace App\Http\Controllers;

use App\Models\Makanan;
use Illuminate\Http\Request;

class MakananController extends Controller
{
    public function index()
    {
        $data = Makanan::all();

        return response()->json([
            'message' => 'Success',
            'data' => $data,
        ]);
    }

    public function store(Request $request)
    {
        $data = $request->validate([
            'nama' => 'required',
            'jenis' => 'required',
            'deskripsi' => 'required',
            'harga' => 'required',
        ]);

        $makanan = Makanan::create($data);

        return response()->json([
            'message' => 'Success',
            'data' => $makanan,
        ]);
    }

    public function show(Makanan $makanan)
    {
        return response()->json([
            'message' => 'Success',
            'data' => $makanan,
        ]);
    }

    public function update(Request $request, Makanan $makanan)
    {
        $data = $request->validate([
            'nama' => 'required',
            'jenis' => 'required',
            'deskripsi' => 'required',
            'harga' => 'required',
        ]);

        $makanan->update($data);

        return response()->json([
            'message' => 'Success',
            'data' => $makanan,
        ]);
    }

    public function destroy(Makanan $makanan)
    {
        $makanan->delete();

        return response()->json([
            'message' => 'Success',
        ]);
    }
}
