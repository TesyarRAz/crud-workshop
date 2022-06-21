<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Makanan>
 */
class MakananFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        return [
            'nama' => $this->faker->name,
            'jenis' => $this->faker->name,
            'deskripsi' => $this->faker->name,
            'harga' => $this->faker->numberBetween(10000, 100000),
        ];
    }
}
