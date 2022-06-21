<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class UserController extends Controller
{
    public function login(Request $request)
    {
        $credentials = $request->validate([
            'username' => 'required',
            'password' => 'required',
        ]);

        if (auth()->attempt($credentials))
        {
            $token = auth()->user()->createToken('user')->plainTextToken;

            return response()->json([
                'message' => 'Login Successful',
                'token' => $token,
            ]);
        }

        return response()->json([
            'message' => 'Login Failed',
        ], 401);
    }
}
